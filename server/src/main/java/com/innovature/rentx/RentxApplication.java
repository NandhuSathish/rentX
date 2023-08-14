package com.innovature.rentx;

import com.innovature.rentx.util.EmailUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.innovature.rentx.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
@EnableAutoConfiguration
public class RentxApplication {

	@Value("${dev.ui.endpoint}")
	private String devEndPoint;

	@Value("${local.host.endpoint}")
	private String localEndPoint;

	@Value("${prod.ui.endpoint}")
	private String prodEndPoint;

	@Value("${dev.ui.adminendpoint}")
	private String admindevEndPoint;

	@Value("${prod.ui.admin.endpoint}")
	private String adminStageEndPoint;
	@Value("${prod.ui.user.endpoint}")
	private String userStageEndPoint;

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*")
						.allowedOrigins(localEndPoint, devEndPoint, prodEndPoint, admindevEndPoint, adminStageEndPoint,
								userStageEndPoint)
						.allowCredentials(true);

			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(RentxApplication.class, args);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LanguageUtil languageUtil() {
		return new LanguageUtil();
	}

	@Bean
	public EmailUtil emailUtil() {
		return new EmailUtil();
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

}
