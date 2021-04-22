package com.youkeda.dewu.service;

import com.youkeda.dewu.model.PaymentRecord;
import com.youkeda.dewu.param.PaymentRecordQueryParam;

import java.util.List;

public interface PaymentRecordService {
    /**
     * 添加或修改支付记录
     *
     * @param paymentRecord 支付记录
     * @return paymentRecord
     */
    PaymentRecord save(PaymentRecord paymentRecord);

    /**
     * 查询支付记录
     *
     * @param queryParam 支付记录参数
     * @return list
     */
    List<PaymentRecord> query(PaymentRecordQueryParam queryParam);
}
