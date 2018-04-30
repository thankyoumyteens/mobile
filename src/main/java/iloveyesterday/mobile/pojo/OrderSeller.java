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
public class OrderSeller {
    private Long id;

    private Long userId;

    private Long sellerId;

    private Long orderNo;

    private Date createTime;

    private Date updateTime;

}