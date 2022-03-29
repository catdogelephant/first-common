package com.mx.goldnurse.exception;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ErrorLog implements Serializable {
    private static final long serialVersionUID = 8294955794460845513L;

    private String id;
    private String serverName;
    private String domain;
    private String apiUrl;
    private String userId;
    private String ip;
    private String errorLogUrl;
    private Date createTime;
}
