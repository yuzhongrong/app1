package com.blockchain.server.otc.common.interceptor;

import com.blockchain.server.base.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConf implements WebMvcConfigurer {
    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public TradingInterceptor tradingInterceptor() {
        return new TradingInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/coin/**")
                .excludePathPatterns("/ad/listBuyAd")
                .excludePathPatterns("/ad/listSellAd")
                .excludePathPatterns("/inner/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/v2/api-docs/**");

//        registry.addInterceptor(tradingInterceptor())
//                .addPathPatterns("/ad/publishBuyAd")
//                .addPathPatterns("/ad/publishSellAd")
//                .addPathPatterns("/ad/cancelAd")
//                .addPathPatterns("/order/buyOrder")
//                .addPathPatterns("/order/sellOrder")
//                .addPathPatterns("/order/cancelBuyOrder")
//                .addPathPatterns("/order/receipt")
//                .addPathPatterns("/order/pay")
//                .addPathPatterns("/appeal/**");
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }
}
