package iloveyesterday.mobile.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsProperties {
    private Long id;

    private Long goodsId;

    private String name;

    private String text;

    private String mainImage;

    private String subImages;

    private BigDecimal price;

    private Long stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}