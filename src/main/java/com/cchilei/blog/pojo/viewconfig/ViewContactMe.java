package com.cchilei.blog.pojo.viewconfig;

/**
 * @Author
 * @Create 2018-05-18 20:25
 * 联系我 的跳转链接
 */
public class ViewContactMe {
    private String qq = "#";
    private String github = "#";
    private String weixin = "#";
    private String weibo = "#";
    private String other = "#";

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "ViewContactMe{" +
                "qq='" + qq + '\'' +
                ", github='" + github + '\'' +
                ", weixin='" + weixin + '\'' +
                ", weibo='" + weibo + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}
