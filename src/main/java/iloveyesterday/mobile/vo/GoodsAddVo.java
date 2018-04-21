package iloveyesterday.mobile.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsAddVo {

    private Long sellerId;
    private Long categoryId;
    private String name;
    private String subtitle;
    private String price;
    private Long stock;
    private String properties;
    private String mainImage;
    private String subImages;
}
