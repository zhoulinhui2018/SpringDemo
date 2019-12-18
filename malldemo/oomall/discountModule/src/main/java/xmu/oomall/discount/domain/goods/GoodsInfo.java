package xmu.oomall.discount.domain.goods;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:商品信息
 * @Data:Created in 14:50 2019/11/29
 * @Modified By:
 **/

public class GoodsInfo {
    private Integer id;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    /*
    *商品名称
    * */
    private String name;
    /*
    *商品序列号
    * */
    private String nameSn;
    /*
    *商品简称
    * */
    private String shortName;
    /*
    *商品描述
    * */
    private String description;
    /*
    *商品简介
    * */
    private String brief;
    /*
    *图片链接
    * */
    private String picUrl;
    /*
    *商品详情
    * */
    private String detail;
    /*
    * 0：上架 1：下架
    * */
    private Boolean status;
    /*
    * 分享链接
    * */
    private String shareUrl;
    /*
    * 商品图片展示廊，就是像淘宝点进商品，滚动展示很多图片
    * */
    private String gallery;
    /*
    *商品分类ID
    * */
    private Integer goodsCategoryId;
    /*
    *品牌ID
    * */
    private Integer brandId;
    /*
    *商品重量
    * */
    private BigDecimal weight;
    /*
    *商品体积
    * */
    private String volume;
    /*
    *特殊邮费计算模板ID
    * */
    private Integer specialFreightId;
    /*
    *0：默认模板计算邮费 1：特殊模板计算邮费
    * */
    private Boolean beSpecial;

    private Boolean beDeleted;

    public static GoodsInfo allGoodsInfo = new GoodsInfo(0);

    /**
     * 用id构造Goods
     * @param id
     */
    public GoodsInfo(Integer id) {
        this.id = id;
        this.setGmtCreate(LocalDateTime.now());
    }

    public GoodsInfo(){
    }
    @Override
    public String toString() {
        return "GoodsInfo{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", name='" + name + '\'' +
                ", nameSn='" + nameSn + '\'' +
                ", shortName='" + shortName + '\'' +
                ", description='" + description + '\'' +
                ", brief='" + brief + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", detail='" + detail + '\'' +
                ", status=" + status +
                ", shareUrl='" + shareUrl + '\'' +
                ", gallery='" + gallery + '\'' +
                ", goodsCategoryId='" + goodsCategoryId + '\'' +
                ", brandId=" + brandId +
                ", weight=" + weight +
                ", volume='" + volume + '\'' +
                ", specialFreightId=" + specialFreightId +
                ", beSpecial=" + beSpecial +
                ", beDeleted=" + beDeleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        GoodsInfo goodsInfo = (GoodsInfo) o;
        return Objects.equals(id, goodsInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameSn() {
        return nameSn;
    }

    public void setNameSn(String nameSn) {
        this.nameSn = nameSn;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public Integer getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Integer goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Integer getSpecialFreightId() {
        return specialFreightId;
    }

    public void setSpecialFreightId(Integer specialFreightId) {
        this.specialFreightId = specialFreightId;
    }

    public Boolean getBeSpecial() {
        return beSpecial;
    }

    public void setBeSpecial(Boolean beSpecial) {
        this.beSpecial = beSpecial;
    }

    public Boolean getBeDeleted() {
        return beDeleted;
    }

    public void setBeDeleted(Boolean beDeleted) {
        this.beDeleted = beDeleted;
    }
}
