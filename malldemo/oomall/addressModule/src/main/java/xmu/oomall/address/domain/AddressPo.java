package xmu.oomall.address.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:地址
 * @Data:Created in 14:50 2019/12/11
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AddressPo {

    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 省份id
     */
    private Integer provinceId;
    /**
     * 县区id
     */
    private Integer countyId;
    /**
     * 地址详情
     */
    private String addressDetail;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 邮政编码
     */
    private String postalCode;
    /**
     * 收件人
     */
    private String consignee;
    /**
     * 是否是默认地址，1表示是，0表示否
     */
    private int beDefault;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean beDeleted;


    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getConsignee() {
        return consignee;
    }

    @Override
    public String toString() {
        return "AddressPo{" +
                "id=" + id +
                ", userId=" + userId +
                ", cityId=" + cityId +
                ", provinceId=" + provinceId +
                ", countyId=" + countyId +
                ", addressDetail='" + addressDetail + '\'' +
                ", mobile='" + mobile + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", consignee='" + consignee + '\'' +
                ", beDefault=" + beDefault +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", beDeleted=" + beDeleted +
                '}';
    }

    public int getBeDefault() {
        return beDefault;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public Boolean getBeDeleted() {
        return beDeleted;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setBeDefault(int beDefault) {
        this.beDefault = beDefault;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public void setBeDeleted(Boolean beDeleted) {
        this.beDeleted = beDeleted;
    }

}
