package com.mx.goldnurse.utils;


import com.mx.goldnurse.jwt.TokenUser;

/**
 * 当前登录用户信息
 * <br>
 * created date 9.1 19:9
 *
 * @author DongJunHao
 */
public class RequestLocal {

    /**
     * 用户ID
     */
    private static ThreadLocal<String> tokenUserIdLocal = new ThreadLocal<>();

    /**
     * 用户名
     */
    private static ThreadLocal<String> tokenUserNameLocal = new ThreadLocal<>();
    /**
     * 用户名
     */
    private static ThreadLocal<TokenUser> tokenUserLocal = new ThreadLocal<>();


    /**
     * 用户ID
     */
    public static String getTokenUserId() {
        return tokenUserIdLocal.get();
    }

    public static void setTokenUserId(String uid) {
        tokenUserIdLocal.set(uid);
    }

    public static void removeTokenUserId() {
        tokenUserIdLocal.remove();
    }

    /**
     * 用户名
     */
    public static String getTokenUserName() {
        return tokenUserNameLocal.get();
    }

    public static void setTokenUserName(String userName) {
        tokenUserNameLocal.set(userName);
    }

    public static void removeTokenUserName() {
        tokenUserNameLocal.remove();
    }

    public static TokenUser getTokenUser() {
        return tokenUserLocal.get();
    }

    public static void setTokenUser(TokenUser tokenUser) {
        tokenUserLocal.set(tokenUser);
    }

    public static void removeTokenUser() {
        tokenUserLocal.remove();
    }

}
