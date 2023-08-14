package com.innovature.rentx.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovature.rentx.security.AccessTokenProcessingFilter;
import com.innovature.rentx.security.AccessTokenUserDetailsService;
import com.innovature.rentx.security.config.SecurityConfig;
import com.innovature.rentx.security.util.TokenGenerator;
import com.innovature.rentx.view.ResponseView;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private static final String LOGIN_CON = "/login";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        AuthenticationManager authenticationManager = authManagerBuilder
                .authenticationProvider(preAuthenticatedAuthenticationProvider())
                .build();

        http.authenticationManager(authenticationManager)
                .addFilter(new AccessTokenProcessingFilter(authenticationManager))
                .securityContext().and()
                .sessionManagement().sessionCreationPolicy(STATELESS).and()
                .exceptionHandling().accessDeniedHandler((request, response, e) -> {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    ObjectMapper mapper = new ObjectMapper();
                    ResponseView errorResponse = new ResponseView(
                            "Current user doesn't have permission to perform this action", "1002");
                    mapper.writeValue(response.getWriter(), errorResponse);
                }).and()
                .headers().and()
                .cors().and()
                .csrf().disable()
                .logout().disable()
                .anonymous().and()
                .authorizeRequests(
                        requests -> requests.antMatchers("/error").permitAll()
                                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .antMatchers("/users").anonymous()
                                .antMatchers("/admin/add").anonymous()
                                .antMatchers("/admin/login").anonymous()
                                .antMatchers(PUT, "/admin/vendor/approve/").access("hasRole('ROLE_ADMIN')")
                                .antMatchers(HttpMethod.PUT, "/admin/vendor/approve/{vendorId}")
                                .access("hasRole('ROLE_ADMIN')")
                                .antMatchers(HttpMethod.PUT, "/admin/user/manageUser/{vendorId}")
                                .access("hasRole('ROLE_ADMIN')")

                                .antMatchers(GET, "/admin/vendor/list/").access("hasRole('ROLE_ADMIN')")
                                .antMatchers(GET, "/admin/vendor/list").access("hasRole('ROLE_ADMIN')")
                                .antMatchers(GET, "/admin/vendor/list/").access("hasRole('ROLE_ADMIN')")
                                .antMatchers(GET, "/admin/user/list").access("hasRole('ROLE_ADMIN')")
                                .antMatchers(GET, "/admin/user/list/").access("hasRole('ROLE_ADMIN')")

                                // .antMatchers(GET,"/admin/vendor/list").access("hasRole('ROLE_VENDOR')")

                                .antMatchers("/user").anonymous()
                                .antMatchers("/user/add").anonymous()
                                .antMatchers("/vendor/registration").anonymous()
                                .antMatchers("/vendor/bankDetails").anonymous()
                                .antMatchers("/vendor/otp/verify").anonymous()
                                .antMatchers("/otp/**/").anonymous()
                                .antMatchers("otp/resend/**").anonymous()
                                .antMatchers("/vendor/bankDetails").anonymous()

                                .antMatchers("/vendor/googleAuth").anonymous()
                                .antMatchers("/user/googleAuth").anonymous()
                                .antMatchers("/forgetPassword").anonymous()
                                .antMatchers("/otp/verify/forgetpassword").anonymous()
                                .antMatchers("/changePassword").anonymous()
                                .antMatchers(POST, "/vendor/store/add").access("hasRole('ROLE_VENDOR')")

                                .antMatchers(GET, "/vendor/store/findAll").anonymous()

                                .antMatchers(GET, "/vendor/store/list").access("hasRole('ROLE_VENDOR')")
                                .antMatchers(POST, "/vendor/product/add").access("hasRole('ROLE_VENDOR')")
                                .antMatchers(POST, "/vendor/**/**").access("hasRole('ROLE_VENDOR')")
                                .antMatchers(PUT, "/vendor/**/**").access("hasRole('ROLE_VENDOR')")

                             
                                .antMatchers(PUT, "/vendor/product/delete/**").access("hasRole('ROLE_VENDOR')")
                                .antMatchers(GET,"/vendor/product/detailView/**").access("hasRole('ROLE_VENDOR')")

                                .antMatchers(POST, "/user/cart/**").access("hasRole('ROLE_USER')")
                                .antMatchers(PUT, "/user/cart/**").access("hasRole('ROLE_USER')")
                                .antMatchers(GET, "/user/cart/**").access("hasRole('ROLE_USER')")
                                .antMatchers(GET, "/user/product/**").access("hasRole('ROLE_USER')")




                                .antMatchers("/admin/forgetPassword").anonymous()
                                .antMatchers("/admin/otp/verify/forgetpassword").anonymous()
                                .antMatchers("/admin/changePassword").anonymous()

                                .antMatchers("/otp/verify").anonymous()
                                .antMatchers(OPTIONS, LOGIN_CON).anonymous()
                                .antMatchers(POST, LOGIN_CON).anonymous()
                                .antMatchers(GET, LOGIN_CON).anonymous()
                                .antMatchers(PUT, LOGIN_CON).anonymous()
                                .antMatchers(GET,"/unAuthorized/category/findAll").permitAll()
                                .antMatchers(GET,"/unAuthorized/store/findAll").permitAll()
                                .antMatchers(GET,"/unAuthorized/subCategory/**").permitAll()
                                .antMatchers(GET,"/user/product/list").permitAll()
                                .antMatchers("/category/**").access("hasRole('ROLE_ADMIN')")
                                .antMatchers("/subcategory/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_VENDOR')")

                                .anyRequest().authenticated()

                );

        return http.build();
    }

    @Bean
    protected AccessTokenUserDetailsService accessTokenUserDetailsService() {
        return new AccessTokenUserDetailsService();
    }

    @Bean
    public HttpFirewall httpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedPercent(true);
        return firewall;
    }

    @Bean
    protected PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider authProvider = new PreAuthenticatedAuthenticationProvider();
        authProvider.setPreAuthenticatedUserDetailsService(accessTokenUserDetailsService());

        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @ConfigurationProperties("app.security")
    public SecurityConfig securityConfig() {
        return new SecurityConfig();
    }

    @Bean
    @ConfigurationProperties("app.security.configuration")
    public TokenGenerator tokenGenerator(SecurityConfig securityConfig) {
        return new TokenGenerator(securityConfig.getTokenGeneratorPassword(), securityConfig.getTokenGeneratorSalt());
    }
}
