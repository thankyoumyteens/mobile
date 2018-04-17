package iloveyesterday.mobile.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetail {
    private Long id;

    private Long goodsId;

    private String images;

    private String text;

    private Date createTime;

    private Date updateTime;
}