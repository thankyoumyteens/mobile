package iloveyesterday.mobile.vo;

import iloveyesterday.mobile.pojo.Category;
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
public class ProductListVo {

    private Long id;

    private Category category;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Long stock;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
