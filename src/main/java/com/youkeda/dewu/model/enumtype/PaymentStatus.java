package com.youkeda.dewu.model.enumtype;

public enum PaymentStatus {
    PENDING("支付中"),
    SUCCESS("支付成功"),
    FAILURE("支付失败"),
    REFUND_SUCCESS("退款成功"),
    REFUND_FAILED("退款失败");

    private final String name;

    PaymentStatus(String name) {
        this.name = name;
    }
}
