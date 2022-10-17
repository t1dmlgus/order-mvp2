package dev.t1dmlgus.config;

import dev.t1dmlgus.common.filter.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 인터셉터 설정
     *
     * @param registry 인터셉터 등록
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/api/v1/login","/api/v1/logout","/api/v1/members");
    }


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api")
//                .allowedOrigins("https://api", "https://...")
//                .allowedMethods("GET","POST")
//                .allowedHeaders("headers")
//                .maxAge()
//    }
}




