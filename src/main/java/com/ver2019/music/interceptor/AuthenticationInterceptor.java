package com.ver2019.music.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ver2019.music.annotation.UserLoginToken;
import com.ver2019.music.entity.User;
import com.ver2019.music.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlermethod=(HandlerMethod) handler;
        Method method=handlermethod.getMethod();
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken=method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                if(token==null){
                    throw new RuntimeException("登陆过期，请重新登陆");
                }
                String userId;
                try {
                    userId= JWT.decode(token).getAudience().get(0);
                    System.out.println(userId);
                } catch (JWTDecodeException j){
                    throw new RuntimeException("401");
                }
                User user=userService.getUserById(userId);
                if(user==null){
                    throw new RuntimeException("用户不存在");
                }
                JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                }catch (JWTVerificationException e){
                    throw new RuntimeException("401");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
