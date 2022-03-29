package com.mx.goldnurse.jwt;

import cn.hutool.json.JSONObject;
import lombok.Data;

import java.io.Serializable;

/**
 * @author peishaopeng
 */
@Data
public class TokenUser implements Serializable {

    private static final long serialVersionUID = -8509194935128439542L;
    String userId;
    String userName;
    JSONObject subject;
}
