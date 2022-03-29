package com.mx.goldnurse.service;

/**
 * 数据操作接口
 * <br>
 * created date 9.2 16:5
 *
 * @author DongJunHao
 */
public interface DataOperateService {

    void insertLogsRecord(String sql, String sqlCommandType, String databaseName, String tokenUserId, String tokenUserName);
}
