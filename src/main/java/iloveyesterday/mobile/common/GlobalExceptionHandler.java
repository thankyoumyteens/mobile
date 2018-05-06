package iloveyesterday.mobile.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理RunTime异常
     *
     * @param e
     */
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
        log.error("exception: ", e);  // 记录错误信息
    }
}