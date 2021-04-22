package com.youkeda.dewu.model.enumtype;

public enum OrderStatus {
    WAIT_BUYER_PAY("待付款"),
    TRADE_CLOSED("订单关闭"),
    TRADE_PAID_SUCCESS("支付成功"),
    TRADE_PAID_FAILED("支付失败");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
