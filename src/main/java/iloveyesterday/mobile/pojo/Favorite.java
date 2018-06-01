package iloveyesterday.mobile.pojo;

import java.util.Date;

public class Favorite {
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long sellerId;

    private Long shopId;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public Favorite(Long id, Long userId, Long goodsId, Long sellerId, Long shopId, Integer type, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.sellerId = sellerId;
        this.shopId = shopId;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Favorite() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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