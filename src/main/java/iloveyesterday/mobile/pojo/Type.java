package iloveyesterday.mobile.pojo;

import java.util.Date;

public class Type {
    private Long id;

    private String name;

    private String values;

    private Boolean status;

    private Date createTime;

    private Date updateTime;

    public Type(Long id, String name, String values, Boolean status, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.values = values;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Type() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values == null ? null : values.trim();
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