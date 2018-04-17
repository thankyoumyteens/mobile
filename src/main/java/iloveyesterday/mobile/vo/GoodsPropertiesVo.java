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
public class GoodsPropertiesVo {

    private Long propertiesId;

    private String name;

    private String text;

    private String mainImage;

    private BigDecimal price;

    private Long stock;

    private Integer status;

    private String statusText;
}
