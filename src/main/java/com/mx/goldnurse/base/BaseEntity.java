package com.mx.goldnurse.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fengrz
 * @since 2020-05-06
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认分页配置 页码
     */
    @TableField(exist = false)
    long current = 1;

    /**
     * 默认分页配置 行数
     */
    @TableField(exist = false)
    long size = 15;

}
