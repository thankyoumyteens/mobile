package iloveyesterday.mobile.vo;

import iloveyesterday.mobile.pojo.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteVo {

    private Long id;

    private Long userId;

    private Goods goods;

    // todo
//    private Shop shop;

    private Integer type;

    private String createTime;
}
