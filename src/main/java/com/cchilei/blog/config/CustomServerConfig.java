package com.cchilei.blog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author
 * @Create 2018-05-26 22:08
 */
@Component
@ConfigurationProperties(prefix = "file.root")
public class CustomServerConfig {
    private String directory;
    private String url;
    private boolean remote;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private Integer zoneIndex;


    public Integer getZoneIndex() {
        return zoneIndex;
    }

    public void setZoneIndex(Integer zoneIndex) {
        this.zoneIndex = zoneIndex;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }
}
