package iloveyesterday.mobile.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemListVo {

    private Long orderItemId;

    private Long goodsId;

    private String productName;

    private String detail;

    private String mainImage;

    private BigDecimal totalPrice;

    private Integer quantity;
}
