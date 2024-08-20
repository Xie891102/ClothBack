package com.cloth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBootJavaConfig implements WebMvcConfigurer {

    @Autowired
    private JsonWebTokenInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
            .addPathPatterns("/cloth/**")    // 攔截所有請求
            .excludePathPatterns(
                "/login",                // 登入頁面
                "/secure/Register",      // 註冊頁面
                "/secure/ForgotPwd",     // 忘記密碼頁面
                "/assets/**",            // 靜態資源
                "/error"                 // 錯誤處理頁面
            );
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//            .allowedOrigins("http://localhost:5173") // 允許的來源（明確列出）
//            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允許的方法
//            .allowedHeaders("*")          // 允許所有標頭
//            .allowCredentials(true);      // 允許憑證（如 cookies）
//    }
}
