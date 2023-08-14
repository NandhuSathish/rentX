package com.innovature.rentx.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class MailTemplateConfig {

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }

    @Bean
    @Qualifier("emailTemplateConfiguration") // Add this qualifier
    public FreeMarkerConfigurationFactoryBean emailTemplateConfiguration() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("classpath:/templates");
        bean.setDefaultEncoding("UTF-8");
        return bean;
    }
}
