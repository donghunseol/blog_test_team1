package shop.mtcoding.blog._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.mtcoding.blog._core.interceptor.LoginInterceptor;

@Configuration // IoC
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/board/**", "/user/**", "reply/**") // 인터셉터가 적용될 URL 패턴 지정
                .excludePathPatterns("/board/{id:\\d+}"); // 인터셉터에서 제외될 URL 패턴 지정

    }
}
