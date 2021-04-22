package com.youkeda.dewu.service.impl;

import com.youkeda.dewu.dao.OrderDAO;
import com.youkeda.dewu.dao.ProductDetailDAO;
import com.youkeda.dewu.dataobject.OrderDO;
import com.youkeda.dewu.model.Order;
import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.ProductDetail;
import com.youkeda.dewu.model.User;
import com.youkeda.dewu.model.enumtype.OrderStatus;
import com.youkeda.dewu.param.QueryOrderParam;
import com.youkeda.dewu.service.OrderService;
import com.youkeda.dewu.service.ProductDetailService;
import com.youkeda.dewu.service.UserService;
import com.youkeda.dewu.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private ProductDetailDAO productDetailDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private UserService userService;
    /**
     * 下单
     *
     * @param order 接收的Order模型
     * @return
     */
    @Override
    public Order add(Order order) {
        if (order == null) {
            return null;
        }
        //根据商品Id查询商品信息
        ProductDetail productDetail = productDetailDAO.selectByPrimaryKey(order.getProductDetailId()).convertToModel();
        if (productDetail == null) {
            return null;
        }

        order.setTotalPrice(productDetail.getPrice());
        order.setId(UUIDUtils.getUUID());
        order.setStatus(OrderStatus.WAIT_BUYER_PAY);

        //生成唯一订单号
        order.setOrderNumber(createOrderNumber());
        OrderDO orderDO = new OrderDO(order);
        int insert = orderDAO.insert(orderDO);

        if (insert == 1) {
            return order;
        }
        return null;
    }

    /**
     * 查询订单
     *
     * @param param 查询参数
     * @return
     */
    @Override
    public Paging<Order> queryRecentPaySuccess(QueryOrderParam param) {
        Paging<Order> result = new Paging<>();

        if (param == null) {
            param = new QueryOrderParam();
        }

        int counts = orderDAO.selectCounts(param);

        if (counts < 1) {
            result.setTotalCount(0);
            return result;
        }

        List<OrderDO> orderDOS = orderDAO.pageQuery(param);
        List<Order> orders = new ArrayList<>();
        List<String> productDetailIds = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();

        orderDOS.forEach(o -> {
            orders.add(o.convertToModel());
            productDetailIds.add(o.getProductDetailId());
            userIds.add(Long.parseLong(o.getUserId()));
        });

        List<ProductDetail> productDetails = productDetailService.queryProductDetail(productDetailIds);
        List<User> users = userService.queryUser(userIds);

        Map<String, ProductDetail> productDetailMap = productDetails.stream()
                .collect(Collectors.toMap(ProductDetail::getId, p -> p));
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        orders.forEach(o -> {
            o.setUser(userMap.get(Long.parseLong(o.getUserId())));
            o.setProductDetail(productDetailMap.get(o.getProductDetailId()));
        });

        result.setData(orders);
        result.setTotalCount(counts);
        result.setPageNum(orders.size());
        result.setPageSize(param.getPageSize());
        result.setTotalPage(orders.size() / param.getPageNum());
        return result;
    }

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber 订单号
     * @return order
     */
    @Override
    public Order getByOrderNumber(String orderNumber) {
        if (StringUtils.isEmpty(orderNumber)) {
            return null;
        }
        OrderDO orderDO = orderDAO.selectByOrderNumber(orderNumber);
        if (orderDO != null) {
            Order order = orderDO.convertToModel();
            return order;
        }
        return null;
    }

    /**
     * 生成唯一订单号
     *
     * @return String
     */
    private String createOrderNumber() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String date = dtf2.format(localDateTime);

        RAtomicLong aLong = redissonClient.getAtomicLong("myOrderPartNum" + date);
        aLong.expire(10, TimeUnit.SECONDS);
        long count = aLong.incrementAndGet();
        String orderId = date + count;

        return orderId;
    }
}
