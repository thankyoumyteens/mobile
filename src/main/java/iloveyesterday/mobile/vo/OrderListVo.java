package iloveyesterday.mobile.vo;

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
public class OrderListVo {

    private Long orderId;

    private Long orderNo;

    private Integer status;

    private String statusMsg;

    private List<OrderItemListVo> orderItemList;

    private BigDecimal totalPrice;

    private Integer count;

    private Date createTime;
}
