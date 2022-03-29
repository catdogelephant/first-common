package com.mx.goldnurse.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author peishaopeng
 * @version 1.0.0
 * @ClassName MybatisPlusConfig
 * @data 3/10/21 16:15
 */
@Configuration
@MapperScan("com.mx.goldnurse.*.mapper")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor mybatisPlusInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
        return paginationInterceptor;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(@Value("${mybatis-plus.mapperScanner.basePackages}") String basePackages) {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(basePackages);
        return mapperScannerConfigurer;
    }
}
