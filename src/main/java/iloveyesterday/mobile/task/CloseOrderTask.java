package iloveyesterday.mobile.task;

import iloveyesterday.mobile.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class CloseOrderTask {

    @Resource
    private IOrderService orderService;


}
