package iloveyesterday.mobile.pojo;

import java.util.Date;

public class Message {
    private Long id;

    private Long fromId;

    private Long toId;

    private Integer type;

    private Integer status;

    private String message;

    private String data;

    private Date sendTime;

    private Date receiveTime;

    public Message(Long id, Long fromId, Long toId, Integer type, Integer status, String message, String data, Date sendTime, Date receiveTime) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.type = type;
        this.status = status;
        this.message = message;
        this.data = data;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
    }

    public Message() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}