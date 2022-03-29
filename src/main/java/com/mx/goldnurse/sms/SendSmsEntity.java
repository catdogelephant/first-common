package com.mx.goldnurse.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <br>
 * created date 2021/12/8 14:00
 *
 * @author JiangYuhao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendSmsEntity implements Serializable {
    private static final long serialVersionUID = -3817568930388007637L;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 外加内容，一般不用
     */
    private String content;
    /**
     * 短信模板
     */
    private String contentCode;
    /**
     * 模板中的属性值设置
     */
    private String param;

    /**
     * 护理服务中心ID
     */
    private String workId;
    /**
     * 发送人类型（0护士，1会员)
     */
    private Integer type;


}
