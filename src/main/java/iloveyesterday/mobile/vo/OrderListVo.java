package iloveyesterday.mobile.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderListVo {

    private Long orderId;

    private Long orderNo;

    private Integer status;

    private String statusMsg;

    private List<OrderItemListVo> orderItemList;

    private BigDecimal totalPrice;

    private Integer count;

    private Date createTime;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemListVo> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemListVo> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
