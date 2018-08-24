package com.chason.book.book.loginInterceptor;

import com.chason.book.book.entity.User;
import com.chason.book.book.result.Result;
import com.chason.book.book.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    private UserService userService;

    /**
     * 进入controller层之前拦截请求
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        log.info("---------------------开始进入请求地址拦截----------------------------");
        String loginUsername = httpServletRequest.getHeader("loginUsername");
        String token = httpServletRequest.getHeader("token");
        User user = null;
        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(loginUsername)) {
            user = userService.getByLoginUsername(loginUsername);
        }

        if (user != null && !StringUtils.isEmpty(user.getToken()) && token.equals(user.getToken())) {
            return true;
        } else {
            PrintWriter printWriter = httpServletResponse.getWriter();
            httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            httpServletResponse.setContentType("application/json; charset=UTF-8");
            printWriter.write("{\"status\": -2,\"msg\": \" " + "登录信息失效，请重新登录" + "\"}");
            printWriter.flush();
            printWriter.close();
//            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/admin");
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("---------------视图渲染之后的操作-------------------------0");
    }
}