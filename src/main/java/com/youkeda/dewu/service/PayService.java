package com.youkeda.dewu.service;

import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.param.PaymentParam;

import java.util.Map;

public interface PayService {
    /**
     * 添加或修改支付记录
     *
     * @param paymentParam 支付参数
     * @return result
     */
    Result payOrder(PaymentParam paymentParam);

    /**
     * 支付宝回调接口
     *
     * @param mapParam 支付宝参数
     * @return Result
     */
    Result alipayCallBack(Map<String, String> mapParam);
}
