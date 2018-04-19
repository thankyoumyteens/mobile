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
public class CartVo {

    private Long id;

    private Long userId;

    private Long productId;

    private String productName;

    private String mainImage;

    private Long quantity;

    private BigDecimal unitPrice;

    private String detail;

    private Long checked;
}
