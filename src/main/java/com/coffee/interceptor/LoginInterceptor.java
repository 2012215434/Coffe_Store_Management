package com.coffee.interceptor;

import com.coffee.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
//@Aspect
public class LoginInterceptor {

    // 用户主页需要拦截登录
    @Pointcut("within (com.coffee.controller.UserController)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object trackInfo(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }
        User user = null;
        if (request != null) {
            user = (User) request.getSession().getAttribute("user");
        }
        if (user == null) {
            System.out.println("-----------用户未登录-----------");
            //手动转发到/login映射路径
            if (attributes != null) {
                if (attributes.getResponse() != null) {
                    attributes.getResponse().setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
                    attributes.getResponse().setHeader("Location", "../login");
                    attributes.getResponse().sendRedirect("/login");
                }
            }
        }
        System.out.println("-----------用户已登录-----------");

        //一定要指定Object返回值，若AOP拦截的Controller return了一个视图地址，那么本来Controller应该跳转到这个视图地址的，但是被AOP拦截了，那么原来Controller仍会执行return，但是视图地址却找不到404了
        //切记一定要调用proceed()方法
        //proceed()：执行被通知的方法，如不调用将会阻止被通知的方法的调用，也就导致Controller中的return会404
        return joinPoint.proceed();
    }


}
