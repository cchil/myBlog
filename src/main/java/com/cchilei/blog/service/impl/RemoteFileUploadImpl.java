package com.cchilei.blog.service.impl;

import com.cchilei.blog.common.Const;
import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.config.CustomServerConfig;
import com.cchilei.blog.pojo.UploadResult;
import com.cchilei.blog.service.FileUploadService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @Author
 * @Create 2018-05-26 21:47
 */

public class RemoteFileUploadImpl implements FileUploadService {

    private static String TOKEN;
    private static UploadManager UPLOAD_MANAGER;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public RemoteFileUploadImpl(CustomServerConfig config, Zone zone) {
        if (config == null || zone == null) {
            throw new NullPointerException("构造初始配置出错：RemoteFileUploadImpl");
        }
        Configuration cfg = new Configuration(zone);
        UPLOAD_MANAGER = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = config.getAccessKey();
        String secretKey = config.getSecretKey();
        String bucket = config.getBucket();
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        if (org.apache.commons.lang3.StringUtils.isBlank(upToken)) {
            throw new NullPointerException("获取Token失败");
        }
        TOKEN = upToken;
    }

    @Override
    public ServerResponse<UploadResult> upload(MultipartFile file, String typeDirectory, String namePrefix) {
        if (StringUtils.isBlank(Const.FILE_ROOT_URL)) {
            throw new NullPointerException("文件上传的URL地址常量没有初始化 在类Const中(FILE_ROOT_URL)");
        }

        if (file != null && !file.isEmpty() && StringUtils.isNotBlank(typeDirectory)) {
            String original = file.getOriginalFilename();
            String suffix = original.substring(original.lastIndexOf("."));
            String newFileName = suffix.replaceAll("\\.", "");
            if (StringUtils.isNotBlank(namePrefix)) {
                newFileName = namePrefix;
            }
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            newFileName += "-" + uuid.substring(0, 8) + suffix;

            String url;
            File temp = null;
            try {
                temp = File.createTempFile(newFileName, suffix);
                file.transferTo(temp);
                url = temp.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("文件下载本地出错" + e);
                temp.delete();
                return ServerResponse.createErrorMessage("上传出错");
            }
            UploadResult uploadResult;
            try {
                Response response = UPLOAD_MANAGER.put(url, typeDirectory + "/" + newFileName, TOKEN);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                logger.debug("上传成功返回的文件名是:{}", putRet.key);

                uploadResult = new UploadResult();
                uploadResult.setNewName(newFileName);
                uploadResult.setOriginalName(original);
                String resultName = String.format("%s%s", Const.FILE_ROOT_URL, URLEncoder.encode(putRet.key, "utf-8"));
                uploadResult.setAbsoluteUrl(resultName);
            } catch (QiniuException ex) {
                Response r = ex.response;
                logger.error(r.toString());
                try {
                    logger.error(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
                return ServerResponse.createErrorMessage("上传出错");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                logger.error("URL 编码出现异常", e);
                return ServerResponse.createErrorMessage("上传出错");
            } finally {
                if (temp != null) {
                    temp.delete();
                }
            }
            if (uploadResult != null) {
                return ServerResponse.createBySuccess(uploadResult);
            } else {
                return ServerResponse.createErrorMessage("上传出错");
            }

        }
        return ServerResponse.createErrorMessage("参数错误");
    }
}
