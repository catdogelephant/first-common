package com.mx.goldnurse.Aspect;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mx.goldnurse.response.QueryParamVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自动分页切面
 * <br>
 * created date 2019/11/4 15:56
 *
 * @author dongjunhao
 */
@Slf4j
@Aspect
@Configuration
public class PaginationAspect<T> {

    @Value("1")
    private int page;

    @Value("10")
    private int size;

    @Pointcut("@annotation(com.mx.goldnurse.annotation.Paginate)")
    public void paginate() {
    }

    @SuppressWarnings("unchecked")
    @Around("paginate()")
    public PageInfo<T> startPage(ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getArgs() == null || joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0].getClass().getSuperclass() != QueryParamVO.class) {
            throw new IllegalAccessException("如果使用分页注解,方法参数必须继承 QueryParamVO !");
        }
        //获取父类
        QueryParamVO queryParamVO = (QueryParamVO) joinPoint.getArgs()[0];
        if (queryParamVO != null && queryParamVO.getSize() != null) {
            this.size = queryParamVO.getSize();
        }
        if (queryParamVO != null && queryParamVO.getCurrent() != null) {
            this.page = queryParamVO.getCurrent();
        }
        log.debug("方法签名： " + joinPoint.getSignature());
        log.debug("参数列表： " + Arrays.asList(joinPoint.getArgs()));
        // 3.向插件赋值
        Page<T> pages = PageHelper.startPage(page, size);
        Object object = joinPoint.proceed();
        if (object instanceof List) {
            List<T> list = (List<T>) object;
            PageInfo<T> pageInfo = pages.toPageInfo();
            pageInfo.setList(list);
            return pageInfo;
        }
        return new PageInfo<T>(new ArrayList<T>());
    }

    /**
     * 通过反射获取属性值
     */
    @SuppressWarnings("RedundantArrayCreation")
    private String getValue(Field field, Object object) throws Exception {
        //获取字段名
        String filedBookName = field.getName();
        //获得字段第一个字母大写
        String firstLetterBook = filedBookName.substring(0, 1).toUpperCase();
        //转换成字段的get方法
        String getMethodName = "get" + firstLetterBook + filedBookName.substring(1);
        Method getSourceMethod = object.getClass().getMethod(getMethodName, new Class[]{});
        //这个对象字段get方法的值
        return getSourceMethod.invoke(object, new Object[]{}).toString();
    }
}
