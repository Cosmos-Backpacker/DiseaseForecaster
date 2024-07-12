package com.forecaster.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.forecaster.pojo.Result;
import com.forecaster.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component      //加Component注解是为了将这个类放入IOC容器中
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override//在处理请求前处理
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI().toString();
        if (url.contains("login")) {        //代表如果请求里有Login就放行
            return true;        //放行
        }


        String jwt = request.getHeader("token");

        if (!StringUtils.hasLength(jwt))//代表jwt为空
        {
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);       //将信息写入响应头
            return false;          //不放行
        }

        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {
            //解析失败
            Result error = Result.error("NOT_LOGIN");
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);
            return false;       //不放行
        }
        //令牌合法，放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
