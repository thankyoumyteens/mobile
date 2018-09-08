package iloveyesterday.mobile.common;

import iloveyesterday.mobile.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Slf4j
public class RedissonManager {

    private Config config = new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        if (redisson == null) {
            init();
        }
        return redisson;
    }

    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port", "6379"));

    @PostConstruct
    private void init() {
        try {
            // 这里如果不加redis://前缀会报URI构建错误 Illegal character in scheme name at index 0
            config.useSingleServer().setAddress("redis://" + redisIp + ":" + redisPort);

            redisson = (Redisson) Redisson.create(config);

            log.info("Redisson init");
        } catch (Exception e) {
            log.error("redisson init error", e);
        }
    }


}
