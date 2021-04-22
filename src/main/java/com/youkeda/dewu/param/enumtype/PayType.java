package com.youkeda.dewu.param.enumtype;

public enum PayType {
    WEIXIN("微信"),
    ALIPAY("支付宝");

    private final String name;

    PayType(String name) {
        this.name = name;
    }
}
