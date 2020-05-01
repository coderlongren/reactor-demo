package com.qunar.flight.user.base;

import com.qunar.flight.user.annotation.CurrentUser;
import com.qunar.flight.user.pojo.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

/**
 * 模仿QClientParam的注解解析器
 */
@Component
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Annotation[] annotations = methodParameter.getParameterAnnotations();
        // 获取所有注解形参
        CurrentUser currentUser = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof CurrentUser) {
                currentUser = (CurrentUser) annotation;
                break;
            }
        }

        // 在尝试获取一次
        if (currentUser == null) {
            currentUser = methodParameter.getParameterAnnotation(CurrentUser.class);
        }
        String reqvalue = nativeWebRequest.getParameter(currentUser.value());

        if(reqvalue == null || reqvalue.isEmpty()){
            return null;
        }
        Class<?> clazz = methodParameter.getParameterType();
        try {
            Object o = null;
            if (o instanceof User) {
                return (User)o;
            }
            return o;
        } catch (Exception e) {
            return null;
        }
    }
}
