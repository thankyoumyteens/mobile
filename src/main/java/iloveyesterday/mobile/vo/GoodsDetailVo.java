package iloveyesterday.mobile.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailVo {

    private Long goodsId;

    private Long categoryId;

    private Long sellerId; // 预留

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private Integer status;

    private String statusText;

    private List<GoodsPropertiesVo> propertiesList;
}
