package com.qunar.flight.user.config;

import com.qunar.flight.user.base.ReqInterceptor;
import com.qunar.flight.user.base.UserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private ReqInterceptor reqInterceptor;

    @Autowired
    private UserMethodArgumentResolver userMethodArgumentResolver;

    /**
     *  拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(reqInterceptor).addPathPatterns("/**");
    }

    /**
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userMethodArgumentResolver);
    }
}
