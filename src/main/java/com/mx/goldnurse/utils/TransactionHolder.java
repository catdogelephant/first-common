package com.mx.goldnurse.utils;

import com.mx.goldnurse.config.SpringContextHolder;
import com.mx.goldnurse.response.FailResponseData;
import com.mx.goldnurse.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.mx.exception.PrivateRuntimeException;
import java.util.function.Supplier;

/**
 * 事务处理器
 * created date 2021/11/11 9:50
 *
 * @author JiangYuhao
 */
@Slf4j
public class TransactionHolder<T> {

    /**
     * 执行事务安全的代码
     *
     * @param supplier
     * @return
     */
    public static <T> T executeTransactionTemplate(Supplier<T> supplier) {
        TransactionTemplate transactionTemplate = SpringContextHolder.getBean(TransactionTemplate.class);
        return transactionTemplate.execute((status -> {
            try {
                return supplier.get();
            } catch (PrivateRuntimeException e) {
                status.setRollbackOnly();
                throw e;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw e;
            }
        }));
    }

    public static ResponseData executeTransactionTemplateResponseData(Supplier<ResponseData> supplier) {
        TransactionTemplate transactionTemplate = SpringContextHolder.getBean(TransactionTemplate.class);
        return transactionTemplate.execute((status -> {
            try {
                return supplier.get();
            } catch (PrivateRuntimeException e) {
                status.setRollbackOnly();
                log.info("数据执行异常", e);
                return new FailResponseData(e.getLogMsg());
            } catch (Exception e) {
                status.setRollbackOnly();
                log.info("数据执行异常", e);
                return new FailResponseData();
            }
        }));
    }

    /**
     * 执行事务安全的代码
     *
     * @param supplier
     * @return
     */
    public static <T> T execute(Supplier<T> supplier) {
        DataSourceTransactionManager dataSourceTransactionManager = SpringContextHolder.getBean(DataSourceTransactionManager.class);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            T t = supplier.get();
            dataSourceTransactionManager.commit(transactionStatus);
            return t;
        } catch (PrivateRuntimeException e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transactionStatus);
            throw e;
        }
    }

    /**
     * 执行事务安全的代码
     *
     * @param supplier
     * @return
     */
    public static ResponseData executeResponseData(Supplier<ResponseData> supplier) {
        DataSourceTransactionManager dataSourceTransactionManager = SpringContextHolder.getBean(DataSourceTransactionManager.class);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            ResponseData responseData = supplier.get();
            dataSourceTransactionManager.commit(transactionStatus);
            return responseData;
        } catch (PrivateRuntimeException e) {
            log.info("数据执行异常", e);
            dataSourceTransactionManager.rollback(transactionStatus);
            return new FailResponseData(e.getLogMsg());
        } catch (Exception e) {
            log.info("数据执行异常", e);
            dataSourceTransactionManager.rollback(transactionStatus);
            return new FailResponseData();
        }
    }
}
