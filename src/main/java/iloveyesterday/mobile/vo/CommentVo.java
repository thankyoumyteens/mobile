package iloveyesterday.mobile.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    private Long commentId;

    private Author author;

    private Long goodsId;

    private String goodsName;

    private String orderCreateTime;

    private Integer star;

    private String images;

    private String text;

    private String properties;

    private String createTime;

    private String updateTime;

    @Getter
    @Setter
    public static class Author {

        private Long userId;

        private String avatar;

        private String nickname;
    }
}
