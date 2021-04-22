package com.youkeda.dewu.api;

import com.youkeda.dewu.model.Order;
import com.youkeda.dewu.model.Paging;
import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.param.QueryOrderParam;
import com.youkeda.dewu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/order")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    @ResponseBody
    public Result<Order> add(@RequestBody Order order, HttpServletRequest request) {
        Result<Order> result = new Result<>();
        result.setSuccess(true);

        if (order == null) {
            result.setSuccess(false);
            result.setMessage("order is null");
            return result;
        }

        Long userId = (Long)request.getSession().getAttribute("userId");
        if (userId == null) {
            result.setSuccess(false);
            result.setMessage("没有获取登录信息");
            return result;
        }

        order.setUserId(userId.toString());
        Order orderResult = orderService.add(order);
        result.setData(orderResult);
        return result;
    }

    @GetMapping("/queryrecentpaysuccess")
    @ResponseBody
    public Result<Paging<Order>> queryRecentPaySuccess(@RequestBody QueryOrderParam param) {
        Result<Paging<Order>> result = new Result<>();
        result.setSuccess(true);

        result.setData(orderService.queryRecentPaySuccess(param));
        return result;
    }
}
