package iloveyesterday.mobile.vo;

import iloveyesterday.mobile.pojo.OrderItem;
import iloveyesterday.mobile.pojo.Shipping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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

    private String paymentTime;

    private String sendTime;

    private String endTime;

    private String closeTime;

    private String createTime;

    //订单的明细
    private List<OrderItem> orderItemList;

    private List<OrderItemListVo> itemList;

    private Shipping shipping;
}
