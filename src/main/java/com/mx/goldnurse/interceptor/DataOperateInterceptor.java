package com.mx.goldnurse.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.mx.goldnurse.service.DataOperateService;
import com.mx.goldnurse.utils.RequestLocal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.SEMICOLON;
import static com.mx.goldnurse.consts.SqlConst.DELETE;
import static com.mx.goldnurse.consts.SqlConst.INSERT;
import static com.mx.goldnurse.consts.SqlConst.UPDATE;
import static com.mx.goldnurse.consts.SqlConst.VALUES;
import static com.mx.goldnurse.consts.UserConst.TOKEN_USER_ID;
import static com.mx.goldnurse.consts.UserConst.TOKEN_USER_NAME;
import static com.mx.goldnurse.utils.SqlUtils.getTableName;
import static com.mx.goldnurse.utils.SqlUtils.indexOfSqlStart;
import static com.mx.goldnurse.utils.SqlUtils.resolveBatchSql;


/**
 * 数据操作拦截器
 * <br>
 * created date 8.31 16:41
 *
 * @author DongJunHao
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
        , @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
@Component
public class DataOperateInterceptor extends AbstractSqlParserHandler implements Interceptor {

    @Value("${goldnurse.tools.config.intercept.tableNames:null}")
    private String[] tableNames;

    private List<String> tableNameList;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    @Resource
    private DataOperateService dataOperateService;

    @PostConstruct
    private void init() {
        tableNameList = Arrays.asList(tableNames);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            //获取用户信息
            String tokenUserId = RequestLocal.getTokenUserId();
            String tokenUserName = RequestLocal.getTokenUserName();
            if (StringUtils.isBlank(tokenUserId) && StringUtils.isBlank(tokenUserName)) {
                tokenUserId = RpcContext.getContext().getAttachment(TOKEN_USER_ID);
                tokenUserName = RpcContext.getContext().getAttachment(TOKEN_USER_NAME);
            }
            log.info("tokenUserId:" + tokenUserId);
            log.info("tokenUserName:" + tokenUserName);
            //获取Statement
            Statement statement = getStatement(invocation);
            //获取执行sql
            String originalSql = manageSql(statement);
            log.info("执行sql:" + originalSql);
            //判断是否是需要执行的表名
            String tableName = getTableName(originalSql);
            log.info("表名:" + tableName);
            if (StringUtils.isBlank(tableName)) {
                return invocation.proceed();
            }
            if (StringUtils.isNotBlank(tableName) && tableNameList != null && !tableNameList.contains(tableName.toLowerCase())) {
                return invocation.proceed();
            }
            //获取sql执行类型
            SqlCommandType sqlCommandType = getSqlCommandType(invocation.getTarget());
            //处理数据
            manageData(originalSql, statement, sqlCommandType, tokenUserId, tokenUserName);
        } catch (Exception e) {
            log.error("数据库操作拦截器异常：", e);
            return invocation.proceed();
        }
        long endTime = System.currentTimeMillis();
        log.info("执行耗时:{}", endTime - startTime);
        //继续执行
        return invocation.proceed();
    }

    /**
     * 处理数据
     */
    private void manageData(String originalSql, Statement statement, SqlCommandType sqlCommandType, String tokenUserId, String tokenUserName) {
        //获取目标库
        String databaseName = null;
        try {
            databaseName = statement.getConnection().getMetaData().getURL();
            databaseName = databaseName.substring(databaseName.lastIndexOf("/") + 1, databaseName.indexOf("?"));
            log.info("目标库:" + databaseName);
        } catch (Exception e) {
            log.error("获取数据库连接信息失败: ", e);
        }
        //使用
        String finalDatabaseName = databaseName;
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                manageData(originalSql, sqlCommandType, finalDatabaseName, tokenUserId, tokenUserName);
            }
        });
    }

    /**
     * 处理数据
     *
     * @param originalSql    sql语句解析
     * @param sqlCommandType sql执行类型
     */
    private void manageData(String originalSql, SqlCommandType sqlCommandType, String databaseName, String tokenUserId, String tokenUserName) {
        String type = "";
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            type = INSERT;
        }
        if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
            type = UPDATE;
        }
        if (SqlCommandType.DELETE.equals(sqlCommandType)) {
            type = DELETE;
        }
        List<String> sqlList = null;
        //判断sql是否是values的批量插入
        if (originalSql.toLowerCase().contains(VALUES)) {
            sqlList = resolveBatchSql(originalSql);
        }
        //判断sql是否是批量的修改
        else if (originalSql.contains(SEMICOLON) && originalSql.toUpperCase().contains(UPDATE)) {
            sqlList = Arrays.asList(originalSql.split(SEMICOLON));
        }
        if (sqlList != null && !sqlList.isEmpty()) {
            for (String sql : sqlList) {
                if (StringUtils.isBlank(sql)) {
                    continue;
                }
                dataOperateService.insertLogsRecord(sql, type, databaseName, tokenUserId, tokenUserName);
            }
        } else {
            dataOperateService.insertLogsRecord(originalSql, type, databaseName, tokenUserId, tokenUserName);
        }

    }

    /**
     * 获取Statement
     *
     * @param invocation invocation
     * @return Statement
     */
    private Statement getStatement(Invocation invocation) {
        Statement statement = null;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObject = SystemMetaObject.forObject(statement);
        if (stmtMetaObject.hasGetter("delegate")) {
            try {
                statement = (Statement) stmtMetaObject.getValue("delegate");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statement;
    }

    /**
     * 获取sql执行类型
     *
     * @param target target
     * @return sql执行类型
     */
    private SqlCommandType getSqlCommandType(Object target) {
        StatementHandler statementHandler = PluginUtils.realTarget(target);
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        return mappedStatement.getSqlCommandType();
    }

    /**
     * 处理sql语句
     *
     * @param statement statement
     * @return 处理后的sql语句
     */
    private String manageSql(Statement statement) {
        //获取执行sql
        String originalSql = String.valueOf(statement);
        originalSql = originalSql.replaceAll("[\\s]", StringPool.SPACE);
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }
        originalSql = originalSql.replace("where", "WHERE");
        return originalSql;
    }
}
