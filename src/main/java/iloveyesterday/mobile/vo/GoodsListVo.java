package iloveyesterday.mobile.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListVo {

    private Long goodsId;

    private Long categoryId;

    private String categoryName;

    private Long sellerId; // 预留

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private String statusMsg;

    private String price; // 单价, 取所有规格中最低的价格

    private Long commentCount; // 评价数量

    private String commentStatus; // xx%好评
}
