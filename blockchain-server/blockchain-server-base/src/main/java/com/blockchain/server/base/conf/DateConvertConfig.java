package com.blockchain.server.base.conf;

import com.blockchain.server.base.interceptor.StringToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class DateConvertConfig {
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @PostConstruct
    public void addDateConvert() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if (initializer != null) {
            System.out.println("===============DateConvertConfig===================");
            GenericConversionService conversionService = (GenericConversionService) initializer.getConversionService();
            conversionService.addConverter(new StringToDateConverter());
        }
    }
}
