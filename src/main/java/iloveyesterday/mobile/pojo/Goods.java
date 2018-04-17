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
public class Goods {
    private Long id;

    private Long categoryId;

    private Long sellerId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}