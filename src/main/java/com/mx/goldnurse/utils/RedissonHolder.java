package com.mx.goldnurse.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * <br>
 * created date 2021/11/19 12:01
 *
 * @author JiangYuhao
 */
@Slf4j
@Component
public class RedissonHolder<T> {


    private static RedissonClient redissonClient;
    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;

    @PostConstruct
    public void init() {
        try {
            String path = "redisson.yml";
            Config config = Config.fromYAML(new ClassPathResource(path).getInputStream());
            config.useSingleServer().setAddress("redis://" + host + ":" + port + "");
            config.useSingleServer().setPassword(password);
            config.setLockWatchdogTimeout(20 * 1000);
            redissonClient = Redisson.create(config);
            log.debug("RedissonHolder初始化成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 安全执行方法
     *
     * @param key 全区唯一key
     */
    public T safeExecutionTask(String key, Supplier<T> function) {
        RLock lock = RedissonHolder.redissonClient.getLock(key);
        try {
            Boolean cacheRes = lock.tryLock(30, 10, TimeUnit.SECONDS);
            if (cacheRes) {
                return function.get();
            }
        } catch (Exception e) {
            log.error("执行失败", e);
            return null;
        } finally {
            //解锁
            lock.unlock();
        }

        return null;
    }

    public static void main(String[] args) {
        RedissonHolder<String> redissonHolder = new RedissonHolder<>();
        String key = "";
        redissonHolder.safeExecutionTask("", () -> {
            return "";
        });
    }
}
