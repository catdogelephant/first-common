package com.mx.goldnurse.utils;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.SEMICOLON;
import static com.mx.goldnurse.consts.SqlConst.DELETE;
import static com.mx.goldnurse.consts.SqlConst.INSERT;
import static com.mx.goldnurse.consts.SqlConst.SELECT;
import static com.mx.goldnurse.consts.SqlConst.UPDATE;
import static com.mx.goldnurse.consts.SqlConst.VALUE;
import static com.mx.goldnurse.consts.SqlConst.VALUES;

/**
 * sql相关工具类
 * <br>
 * created date 9.22 10:32
 *
 * @author DongJunHao
 */
@Slf4j
public class SqlUtils {

    /**
     * 反射获取表名
     *
     * @param table table
     * @return 表名
     */
    public static String getTableName(Table table) {
        try {
            Field field = table.getClass().getDeclaredField("partItems");
            field.setAccessible(true);
            Object o = field.get(table);
            if (o instanceof List) {
                List<?> list = (List<?>) o;
                if (!list.isEmpty()) {
                    String tableName = list.get(0).toString();
                    tableName = tableName.replaceAll("`", "");
                    return tableName.toLowerCase();
                }
            }
            return table.getName().toLowerCase();
        } catch (Exception e) {
            log.error("反射获取表名失败", e);
        }
        return null;
    }

    /**
     * 获取数据库表名
     *
     * @param sql sql
     * @return 表名
     */
    public static String getTableName(String sql) {
        net.sf.jsqlparser.statement.Statement statement = null;
        try {
            if (sql.contains(SEMICOLON) && sql.toUpperCase().contains(UPDATE)) {
                sql = sql.split(SEMICOLON)[0];
            }
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            log.error(String.format("解析sql失败,sql = %s", sql), e);
        }
        try {
            //新增
            if (statement instanceof Insert) {
                Insert insert = (Insert) statement;
                return SqlUtils.getTableName(insert.getTable());
            }
            //修改
            if (statement instanceof Update) {
                Update update = (Update) statement;
                return SqlUtils.getTableName(update.getTable());
            }
            //删除
            if (statement instanceof Delete) {
                Delete delete = (Delete) statement;
                return SqlUtils.getTableName(delete.getTable());
            }
        } catch (Exception e) {
            log.error("获取表名失败!", e);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getTableName("INSERT INTO settlement ( id, source_id, sub_source_id, amount, dictionaries_id, remark, settlement_type, settlement_status, than, STATUS, create_time )\n" +
                "VALUES\n" +
                "\t(\n" +
                "\t\t'9c5ee97a6b0c4f22b115dc0f27c2dec4',\n" +
                "\t\t'c02f4947f7a4462e936f91892c0c163f',\n" +
                "\t\t'312d2ed165cd42c3a1ac7f301ab09c03',\n" +
                "\t\t1251.09,\n" +
                "\t\t'11111111111111111111111111111115',\n" +
                "\t\t'服务订单-里程费-服务人员分成',\n" +
                "\t\t2,\n" +
                "\t\t0,\n" +
                "\t\t0.66,\n" +
                "\t\t0,\n" +
                "\t'2021-06-09 15:46:57' \n" +
                "\t)"));
    }

    /**
     * 获取sql语句开头部分
     *
     * @param sql ignore
     * @return ignore
     */
    public static int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet<>();
        set.add(upperCaseSql.indexOf(SELECT));
        set.add(upperCaseSql.indexOf(UPDATE));
        set.add(upperCaseSql.indexOf(INSERT));
        set.add(upperCaseSql.indexOf(DELETE));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        }
        List<Integer> list = new ArrayList<>(set);
        list.sort(Comparator.naturalOrder());
        return list.get(0);
    }

    /**
     * 分解批量插入的sql
     *
     * @param batchSql 批量插入的sql
     * @return 返回分解后的sql集合
     */
    public static List<String> resolveBatchSql(String batchSql) {
        String before = "", after = "";
        List<String> valueList;
        List<String> sqlList = new ArrayList<>();
        if (batchSql.toLowerCase().contains(VALUES)) {
            String[] sqls = batchSql.toLowerCase().split(VALUES);
            if (sqls.length == 2) {
                before = sqls[0];
                after = sqls[1];
            }
        }
        if (StringUtils.isNotBlank(before) && StringUtils.isNotBlank(after)) {
            String[] values = after.split("\\)(\\s*),(\\s*)\\(", -1);
            valueList = Arrays.asList(values);
            for (String value : valueList) {
                value = value.trim();
                if (!value.startsWith("(")) {
                    value = "(" + value;
                }
                if (!value.endsWith(")")) {
                    value = value + ")";
                }
                sqlList.add(before + VALUE + value);
            }
        }
        return sqlList;
    }
}
