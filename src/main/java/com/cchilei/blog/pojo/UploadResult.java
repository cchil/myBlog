package com.cchilei.blog.pojo;

/**
 * @Author
 * @Create 2018-05-15 18:24
 * 文件上传成功后的返回结果
 */
public class UploadResult {
    private String originalName;
    private String newName;
    private String absoluteUrl;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    @Override
    public String toString() {
        return "UploadResult{" +
                "originalName='" + originalName + '\'' +
                ", newName='" + newName + '\'' +
                ", absoluteUrl='" + absoluteUrl + '\'' +
                '}';
    }
}
