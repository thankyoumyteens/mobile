package iloveyesterday.mobile.pojo;

import java.util.Date;

public class Review {
    private Long id;

    private Long orderItemId;

    private Long userId;

    private Integer star;

    private String images;

    private String detail;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Review(Long id, Long orderItemId, Long userId, Integer star, String images, String detail, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.orderItemId = orderItemId;
        this.userId = userId;
        this.star = star;
        this.images = images;
        this.detail = detail;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Review() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}