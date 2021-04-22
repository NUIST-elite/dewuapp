package com.youkeda.dewu.service;

import com.youkeda.dewu.model.Result;
import com.youkeda.dewu.model.User;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {

    /**
     * 注册用户
     *
     * @param userName 用户名
     * @param pwd 密码
     * @return result
     */
    Result<User> register(String userName, String pwd);

    /**
     * 执行登录逻辑，登录成功返回 User 对象
     *
     * @param userName 用户名
     * @param pwd 密码
     * @return result
     */
    Result<User> login(String userName, String pwd);

    /**
     * 判断是否登录
     *
     * @param request
     * @return
     */
    Boolean checkLogin(HttpServletRequest request);

    /**
     * 获取多个用户信息
     *
     * @param userIds  查询参数
     * @return
     */
    List<User> queryUser(List<Long> userIds);
}
