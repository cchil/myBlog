package com.cchilei.blog.service;

import com.cchilei.blog.common.ServerResponse;
import com.cchilei.blog.pojo.UploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author
 * @Create 2018-05-09 20:06
 */
public interface FileUploadService {

    /**
     * 最基础的文件上传
     *
     * @param file
     * @param typeDirectory
     * @param namePrefix
     * @return 成功后返回的是文件名称地址 可直接存入数据库的
     */
    ServerResponse<UploadResult> upload(MultipartFile file, String typeDirectory, String namePrefix);
}
