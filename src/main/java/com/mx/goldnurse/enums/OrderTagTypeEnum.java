package com.mx.goldnurse.enums;

/**
 * @author sx
 * @Description 订单标志枚举类
 * @date 2020/8/20 16:51
 */
public enum OrderTagTypeEnum {

    //默认
    ORDER_DEFAULT("DEFAULT", "默"),
    //派单
    ORDER_ASSIGNMENT("ASSIGNMENT", "派"),
    //抢单
    ORDER_SNATCH("SNATCH", "抢"),
    //指定(用户自己选择)
    ORDER_APPOINT("APPOINT", "指"),
    //分享
    ORDER_SHARE("SHARE", "享"),
    //内部
    ORDER_INSIDE("INSIDE", "内"),
    //夜间服务
    ORDER_NIGHT("NIGHT", "夜"),
    //初购
    ORDER_FIRST("FIRST", "初"),
    //复购
    ORDER_REPEAT("REPEAT", "复");

    private String type;

    private String name;

    OrderTagTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
