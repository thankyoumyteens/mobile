package iloveyesterday.mobile.vo;

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

    private Author author;

    private Long goodsId;

    private String goodsName;

    private Date orderCreateTime;

    private Integer star;

    private String images;

    private String text;

    private String properties;

    private Date createTime;

    private Date updateTime;

    @Getter
    @Setter
    public static class Author {

        private Long userId;

        private String avatar;

        private String nickname;
    }
}
