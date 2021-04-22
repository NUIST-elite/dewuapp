package com.youkeda.dewu.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.youkeda.dewu.model.Order;
import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.param.QueryOrderParam;

public interface OrderService {
    /**
     * 下单
     *
     * @param order    接收的Order模型
     * @return
     */
    Order add(Order order);

    /**
     * 查询订单
     *
     * @param param    查询参数
     * @return
     */
    Paging<Order> queryRecentPaySuccess(QueryOrderParam param);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单号
     * @return order
     */
    Order getByOrderNumber(String orderNumber);
}
