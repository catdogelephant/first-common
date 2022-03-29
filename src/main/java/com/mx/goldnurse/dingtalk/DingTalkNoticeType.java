package com.mx.goldnurse.dingtalk;

public enum DingTalkNoticeType {

    /** 文本消息 */
    TEXT("text"),
    /** 链接  */
    LINK("link"),
    /** markdown */
    MARKDOWN("markdown");

    private String value;

    DingTalkNoticeType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
