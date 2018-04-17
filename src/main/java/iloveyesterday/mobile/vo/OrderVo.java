package iloveyesterday.mobile.vo;

import iloveyesterday.mobile.pojo.OrderItem;
import iloveyesterday.mobile.pojo.Shipping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private Integer paymentType;

    private String paymentTypeMsg;

    private Integer postage;

    private Integer status;

    private String statusMsg;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    //订单的明细
    private List<OrderItem> orderItemList;

    private Shipping shipping;
}
