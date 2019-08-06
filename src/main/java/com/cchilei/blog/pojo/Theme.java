package com.cchilei.blog.pojo;

import java.io.Serializable;

public class Theme implements Serializable {
    private String mainBg;

    private String themeCss;

    private String themeJs;

    private Integer id;

    public Theme(String mainBg, String themeCss, String themeJs, Integer id) {
        this.mainBg = mainBg;
        this.themeCss = themeCss;
        this.themeJs = themeJs;
        this.id = id;
    }

    public Theme() {
        super();
    }

    public String getMainBg() {
        return mainBg;
    }

    public void setMainBg(String mainBg) {
        this.mainBg = mainBg == null ? null : mainBg.trim();
    }

    public String getThemeCss() {
        return themeCss;
    }

    public void setThemeCss(String themeCss) {
        this.themeCss = themeCss == null ? null : themeCss.trim();
    }

    public String getThemeJs() {
        return themeJs;
    }

    public void setThemeJs(String themeJs) {
        this.themeJs = themeJs == null ? null : themeJs.trim();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}