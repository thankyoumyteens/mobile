package iloveyesterday.mobile.vo;

import iloveyesterday.mobile.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long commentId;

    private User author;

    private Long productId;

    private String productName;

    private Date orderCreateTime;

    private Integer star;

    private String images;

    private String detail;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}
