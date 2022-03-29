package com.mx.goldnurse.mq.rocket.producer;

import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.mx.goldnurse.mq.rocket.config.MqConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName ProducerClient
 * @Description TODO
 * @data 12/18/20 16:10
 */
@Configuration
public class ProducerClient {

    @Resource
    private MqConfig mqConfig;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean buildProducer() {
        //ProducerBean用于将Producer集成至Spring Bean中
        ProducerBean producer = new ProducerBean();
        producer.setProperties(mqConfig.getMqPropertie());
        return producer;
    }

}
