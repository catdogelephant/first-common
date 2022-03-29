package com.mx.goldnurse.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;


/**
 * <br>
 * created date 2021/10/11 16:25
 *
 * @author JiangYuhao
 */
@Slf4j
@Configuration
//@EnableScheduling
public class MxMessageConfig implements InitializingBean {


    private static String serverAddr;
    private static String namespace;
    private static Properties properties;

    @Value("${spring.cloud.nacos.config.server-addr}")
    public void setServerAddr(String serverAddr) {
        MxMessageConfig.serverAddr = serverAddr;
    }

    @Value("${spring.cloud.nacos.config.namespace}")
    public void setNamespace(String namespace) {
        MxMessageConfig.namespace = namespace;
    }

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Override
    public void afterPropertiesSet() throws Exception {
        listen();
    }

    public void listen() {

        // 监听 nacos 对应的一个 dataId 的数据变动
        Listener listenerJsonTest = new Listener() {
            @Override
            public Executor getExecutor() {
                return threadPoolTaskExecutor;
            }

            @Override
            public void receiveConfigInfo(String content) {
                // 收到变动
                Properties propertiesNew = loadUTF(content);
                compareDifferent(MxMessageConfig.properties, propertiesNew);
                MxMessageConfig.properties = propertiesNew;
            }
        };

        Properties properties = new Properties();
        // nacos服务器地址，127.0.0.1:8848
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        // 配置中心的命名空间id
        properties.put(PropertyKeyConst.NAMESPACE, namespace);

        ConfigService configService = null;
        try {
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        try {
            String dataId = "goldnurse-message.properties";
            String content = configService.getConfig(dataId, "DEFAULT_GROUP", 1000);
            Properties propertiesNew = loadUTF(content);
            MxMessageConfig.properties = propertiesNew;
            configService.addListener(dataId, "DEFAULT_GROUP", listenerJsonTest);
            log.info("美鑫配置中心监听启动成功");
        } catch (NacosException e) {
            e.printStackTrace();
        }

    }


    /**
     * 配置文件每隔5秒钟刷新一次
     */
//    @Scheduled(fixedRate = 5 * 1000)
//    public void refresh() {
//        System.out.println(getProperties().getProperty("10000"));
//        getConfig();
//    }
    public static Properties getProperties() {
        if (MxMessageConfig.properties == null) {
            getConfig();
        }
        return MxMessageConfig.properties;
    }


    private static Properties getConfig() {

        try {
            Properties properties = new Properties();
            // nacos服务器地址，127.0.0.1:8848
            properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
            // 配置中心的命名空间id
            properties.put(PropertyKeyConst.NAMESPACE, namespace);

            ConfigService configService = NacosFactory.createConfigService(properties);
            // 根据dataId、group定位到具体配置文件，获取其内容. 方法中的三个参数分别是: dataId, group, 超时时间
            String dataId = "goldnurse-message.properties";
            String content = configService.getConfig(dataId, "DEFAULT_GROUP", 3000L);

            if (StringUtils.isBlank(content)) {
                log.info("配置信息为空或者配置项不存在:{}", dataId);
            }
            Properties propertiesNew = loadUTF(content);
            compareDifferent(MxMessageConfig.properties, propertiesNew);
            MxMessageConfig.properties = propertiesNew;
            return properties;
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 比对配置文件更新
     *
     * @param properties
     * @param propertiesNew
     */
    private static void compareDifferent(Properties properties, Properties propertiesNew) {
        if (properties == null) {
            //第一次进入没有初始值
            return;
        }
        Set<String> keys = properties.stringPropertyNames();
        Set<String> keyNews = propertiesNew.stringPropertyNames();
        for (String key : keys) {
            String value = properties.getProperty(key);
            String valueNew = propertiesNew.getProperty(key);
            if (StringUtils.isBlank(valueNew)) {
                log.info("配置[key:{}]已删除", key);

            }
            if (!value.equals(valueNew) && valueNew != null) {
                log.info("配置[key:{}]被改变，改变前before：{}，改变后after：{}", key, value, valueNew);

            }
        }
        for (String key : keyNews) {
            String value = properties.getProperty(key);
            String valueNew = propertiesNew.getProperty(key);
            if (StringUtils.isBlank(value)) {
                log.info("配置[key:{}]已添加，内容为：{}", key, valueNew);
            }
        }
    }


    private static Properties load(String propertiesString) {
        //存在中文乱码
        Properties properties = new Properties();
        try {
            properties.load(new ByteArrayInputStream(propertiesString.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static Properties loadUTF(String propertiesString) {
        Properties properties = null;
        try {
            properties = new Properties();
            properties.load(new StringReader(propertiesString));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


}
