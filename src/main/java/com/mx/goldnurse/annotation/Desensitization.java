package com.mx.goldnurse.annotation;

import java.lang.annotation.*;

/**
 * @author 陈龙飚
 * @version V1.0
 * @class: Desensitization
 * @date 2022/1/4
 * @project goldnurse-commons
 * @description
 * 脱敏注解
 * 使用方式：
 *  在Controller方法上使用。脱敏手机号。传 phoneNode(),脱敏身份证号用 cardNode()
 *  1. 直接用字段名称    eg: phoneNodeName                     适用返回结构为对象且手机号或身份证号字段在当前对象中
 *  2. 用 ::  分隔     eg: objName::phoneNodeName             适用返回结构为对象嵌套对象且手机号或身份证号字段在嵌套对象中        对应结构为 {"objName":{"phoneNodeName":"123456768765"}}
 *  3. 用 ;;  分隔     eg: listName;;phoneNodeName            适用返回结构为对象嵌套不分页列表且手机号或身份证号字段在列表对象中   对应结构为 {"listName":[{"phoneNodeName":"123456768765"},{"phoneNodeName":"123456768765"}]}
 *  4. 用 ;P; 分隔     eg: pageInfoObjName;P;phoneNodeName    适用返回结构为对象嵌套分页列表且手机号或身份证号字段在列表对象中     对应结构为 {"pageInfoObjName":{"listName":[{"phoneNodeName":"123456768765"},{"phoneNodeName":"123456768765"}]}}
 *  5. 用 L,, 开头     eg: L,,phoneNodeName                   适用返回结构为分页列表且手机号或身份证号字段在列表对象中            对应结构为 [{"phoneNodeName":"123456768765"},{"phoneNodeName":"123456768765"}]}
 *
 *  注：1. 以上分页列表指的是用谷歌分页对象或者mybatisPlus分页对象的分页
 *     2. 以上以手机号举例。身份证号同样适用;
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Desensitization {

    /**
     * 手机号字段
     * @return
     */
    String[] phoneNode() default "";

    /**
     * 身份证字段
     * @return
     */
    String[] cardNode() default "";
}
