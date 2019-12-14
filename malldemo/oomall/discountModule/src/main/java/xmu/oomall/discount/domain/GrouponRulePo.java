package xmu.oomall.discount.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Author: 数据库与对象模型标准组
 * @Description:团购规则信息
 * @Data:Created in 14:50 2019/11/29
 * @Modified By:
 **/

public class GrouponRulePo {
    private Integer id;

    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Boolean isDeleted;
    /*
     *团购开始时间
     * */
    private LocalDateTime startTime;
    /*
     *团购结束时间
     * */
    private LocalDateTime endTime;
    /*
    *判断团购是否还在进行中
    * */
    private Boolean status;
    /*
    * 团购等级（满多少人组团多少折扣）
    * */
    private String grouponLevelStrategy;
    /*
    *团购产品id
    * */
    private Integer productId;

    @Override
    public String toString() {
        return "GrouponRulePo{" +
                "id=" + id +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", isDeleted=" + isDeleted +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", grouponLevelStrategy='" + grouponLevelStrategy + '\'' +
                ", productId=" + productId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GrouponRulePo that = (GrouponRulePo) o;
        return Objects.equals(id, that.id);
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getGrouponLevelStrategy() {
        return grouponLevelStrategy;
    }

    public void setGrouponLevelStrategy(String grouponLevelStrategy) {
        this.grouponLevelStrategy = grouponLevelStrategy;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}