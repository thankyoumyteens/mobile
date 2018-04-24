package iloveyesterday.mobile.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class Message<T> {

    // 发送方Id
    private Long from;

    // 接收方Id
    private Long to;

    // 消息类型
    private int type;

    // 消息正文
    private String message;

    // 附加数据
    private T data;

    // 处理状态
    private int status;

    // 消息发送时间
    private Date sendTime;

    // 消息接收时间
    private Date receiveTime;

    public interface MessageType {
        int DEFAULT = 0;
    }

    public interface MessageStatus {
        // 已发送, 未收到
        int SENT = 1;
        // 已收到
        int RECEIVED = 2;
        // 已处理
        int DONE = 3;
        // 忽略
        int IGONRE = 4;
    }
}
