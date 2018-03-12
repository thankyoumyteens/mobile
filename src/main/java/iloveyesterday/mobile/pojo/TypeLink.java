package iloveyesterday.mobile.pojo;

import java.util.Date;

public class TypeLink {
    private Long id;

    private Long productId;

    private Long typeId;

    private Boolean status;

    private Date createTime;

    private Date updateTime;

    public TypeLink(Long id, Long productId, Long typeId, Boolean status, Date createTime, Date updateTime) {
        this.id = id;
        this.productId = productId;
        this.typeId = typeId;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TypeLink() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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