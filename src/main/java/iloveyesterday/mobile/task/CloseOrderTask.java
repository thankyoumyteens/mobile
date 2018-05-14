package iloveyesterday.mobile.task;

import iloveyesterday.mobile.common.Const;
import iloveyesterday.mobile.common.RedissonManager;
import iloveyesterday.mobile.service.IOrderService;
import iloveyesterday.mobile.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CloseOrderTask {

    @Resource
    private IOrderService orderService;

    @Resource
    private RedissonManager redissonManager;

    // 每1分钟(每个1分钟的整数倍)
    @Scheduled(cron = "0 */1 * * * ?")
    public void closeOrderTask() {
        RLock lock = redissonManager.getRedisson().getLock(Const.RedisLock.CLOSE_ORDER_TASK_LOCK);
        boolean getLock = false;
        try {
            if (getLock = lock.tryLock(0, 50, TimeUnit.SECONDS)) {
                log.info("Redisson get lock success:{},ThreadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
                int hour = Integer.parseInt(PropertiesUtil.getProperty("close.order.task.time.hour", "2"));
                orderService.closeOrder(hour);
            } else {
                log.info("Redisson get lock failed:{},ThreadName:{}", Const.RedisLock.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            log.error("Redisson get lock exception", e);
        } finally {
            if (!getLock) {
                return;
            }
            lock.unlock();
            log.info("Redisson lock close");
        }
    }

}
