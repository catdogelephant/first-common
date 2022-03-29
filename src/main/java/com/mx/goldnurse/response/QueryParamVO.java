package com.mx.goldnurse.response;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * 分页查询参数
 * <br>
 * created date 2019/11/4 16:19
 *
 * @author dongjunhao
 */
@Data
public class QueryParamVO implements Serializable {

    private static final long serialVersionUID = -7322728311644025062L;

    /**
     * 当前页
     */
    @TableField(exist = false)
    private Integer current =1;

    /**
     * 每页显示条数
     */
    @TableField(exist = false)
    private Integer size =10;

}
