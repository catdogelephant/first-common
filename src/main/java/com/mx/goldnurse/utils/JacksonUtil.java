package com.mx.goldnurse.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName JackJsonUtil
 * @data 2021/3/7 9:32 下午
 */
public class JacksonUtil<T> {

    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);

    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // 忽略json中在对象不存在对应属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略空bean转json错误
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // 忽略json字符串中不识别的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // PrettyPrinter 格式化输出
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // NULL不参与序列化
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        // 指定时区
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        // 日期类型字符串处理
//        mapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        mapper.registerModule(javaTimeModule);
    }

    /**
     * 序列化
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null) {
                long timestamp = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                gen.writeNumber(timestamp);
            }
        }
    }

    /**
     * json转对象
     *
     * @param jsonStr   json串
     * @param classType 对象类型
     * @return 对象
     */
    public static <T> T toEntity(String jsonStr, Class<T> classType) {

        if (StringUtils.isEmpty(jsonStr)) {
            log.warn("Json string {} is empty!", classType);
            return null;
        }

        try {
            return mapper.readValue(jsonStr, classType);
        } catch (IOException e) {
            log.error("json to entity error.", e);
        }
        return null;
    }

    /**
     * json转化为带泛型的对象
     *
     * @param jsonStr json字符串
     * @param typeReference 转化类型
     * @return 对象
     */
    public static <T> T toEntity(String jsonStr, TypeReference<T> typeReference) {
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isBlank(jsonStr) || typeReference == null) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonStr, typeReference);
        } catch (JsonProcessingException e) {
            log.error("json to entity error.", e);
        }
        return null;
    }

    /**
     * 对象转json
     *
     * @param obj 对象
     * @return json串
     */
    public static String toJson(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.error("obj to json error.", e);
        }
        return null;
    }

    /**
     * 对象转json(格式化的json)
     *
     * @param obj 对象
     * @return 格式化的json串
     */
    public static String toJsonWithFormat(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.error("obj to json error.", e);
        }
        return null;
    }

}
