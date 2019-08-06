package com.cchilei.blog.pojo.viewconfig;

import com.cchilei.blog.pojo.Setting;
import org.springframework.stereotype.Component;

/**
 * @Author
 * @Create 2018-05-18 20:18
 * 主配置类 --将其会写在数据库 Json格式转化
 */
@Component
public class ViewConfiguration {
    /**
     * 显示在主页的标题
     */
    private String mainTitle;

    /**
     * 显示在主页上的图片地址 绝对的
     */
    private String mainHeadImage;

    /**
     * 是否显示底部Footer布局 true
     * true 显示，false隐藏
     */
    private boolean isShowFooter;

    /**
     * 首页置顶的文章ID
     */
    private Integer contentTopId;

    /**
     * 关于本站的显示内容
     */
    private ViewAboutStation viewAboutStation;

    /**
     * 首页轮播图的配置
     */
    private ViewCarousel viewCarousel;

    /**
     * 联系我的跳转链接配置
     */
    private ViewContactMe viewContactMe;

    /**
     * 内容列表中的图片配置
     */
    private ViewContentItem viewContentItem;

    /**
     * 展示在右侧关于我中的最底部一栏中的内容配置
     */
    private ViewOther viewOther;

    /**
     * 标签云的配置显示
     */
    private ViewTagCloud viewTagCloud;

    /**
     * 数据库中获取的原始的数据
     */
    private Setting setting;

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getMainHeadImage() {
        return mainHeadImage;
    }

    public void setMainHeadImage(String mainHeadImage) {
        this.mainHeadImage = mainHeadImage;
    }

    public boolean isShowFooter() {
        return isShowFooter;
    }

    public void setShowFooter(boolean showFooter) {
        isShowFooter = showFooter;
    }

    public ViewAboutStation getViewAboutStation() {
        return viewAboutStation;
    }

    public void setViewAboutStation(ViewAboutStation viewAboutStation) {
        this.viewAboutStation = viewAboutStation;
    }

    public ViewCarousel getViewCarousel() {
        return viewCarousel;
    }

    public void setViewCarousel(ViewCarousel viewCarousel) {
        this.viewCarousel = viewCarousel;
    }

    public ViewContactMe getViewContactMe() {
        return viewContactMe;
    }

    public void setViewContactMe(ViewContactMe viewContactMe) {
        this.viewContactMe = viewContactMe;
    }

    public ViewContentItem getViewContentItem() {
        return viewContentItem;
    }

    public void setViewContentItem(ViewContentItem viewContentItem) {
        this.viewContentItem = viewContentItem;
    }

    public ViewOther getViewOther() {
        return viewOther;
    }

    public void setViewOther(ViewOther viewOther) {
        this.viewOther = viewOther;
    }

    public ViewTagCloud getViewTagCloud() {
        return viewTagCloud;
    }

    public void setViewTagCloud(ViewTagCloud viewTagCloud) {
        this.viewTagCloud = viewTagCloud;
    }

    public Integer getContentTopId() {
        return contentTopId;
    }

    public void setContentTopId(Integer contentTopId) {
        this.contentTopId = contentTopId;
    }
}
