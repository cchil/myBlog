package com.cchilei.blog.service.impl;

import com.cchilei.blog.common.Const;
import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.pojo.UploadResult;
import com.cchilei.blog.service.FileUploadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author
 * @Create 2018-05-09 20:06
 */

public class FileUploadServiceImpl implements FileUploadService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 最基础的文件上传
     *
     * @param file
     * @param typeDirectory 要上传的文件夹名称 从常量中获取
     * @param namePrefix    选择性给值 文件的前缀
     * @return 成功后返回的是文件名称地址 可直接存入数据库的
     */
    @Override
    public ServerResponse<UploadResult> upload(MultipartFile file, String typeDirectory, @Nullable String namePrefix) {
        if (StringUtils.isBlank(Const.DIRECTORY_URI)) {
            throw new NullPointerException("文件上传的目录地址常量没有初始化 在类Const中(DIRECTORY_URI)");
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

            File root = new File(Const.DIRECTORY_URI, typeDirectory);
            if (!root.exists()) {
                root.setWritable(true);
                root.mkdirs();
            }
            File target = new File(root, newFileName);
            try {
                file.transferTo(target);
            } catch (IOException e) {
                logger.error("上传文件出错!", e);
                return ServerResponse.createBySuccessMessage("上传出错");
            }
            logger.debug("文件上传成功，文件名:{},上传路径:{} .", newFileName, target.getAbsoluteFile());
            UploadResult uploadResult = new UploadResult();
            uploadResult.setOriginalName(original);
            uploadResult.setNewName(newFileName);
            uploadResult.setAbsoluteUrl(Const.FILE_ROOT_URL+(typeDirectory + File.separator + newFileName));
            return ServerResponse.createBySuccess("上传成功",uploadResult);
        }
        return ServerResponse.createErrorMessage("参数错误");
    }

}
