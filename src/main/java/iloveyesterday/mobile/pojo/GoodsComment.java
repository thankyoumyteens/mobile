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
public class GoodsComment {
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long propertiesId;

    private String images;

    private String text;

    private Integer star;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}