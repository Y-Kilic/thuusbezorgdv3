package com.example.deliveryservice.config;

import com.example.deliveryservice.security.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
public class UserConfig implements WebMvcConfigurer {

    private UserRepository users;

    public UserConfig(UserRepository users) {
        this.users = users;
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new UserResolver(users));
    }
}
