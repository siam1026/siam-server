//package com.siam.package_common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 开启跨域访问拦截器
//     *
//     * @author
//     * @date 2020/4/29 9:50
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(source);
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        // 将登录框关闭
////        httpSecurity.formLogin().disable();
//
//        //开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
//        httpSecurity.csrf().disable();
//
//        //开启跨域访问
//        httpSecurity.cors();
//
//        //不使用默认退出，自定义退出
//        httpSecurity.logout().disable();
//
//        // 允许所有请求通过
//        httpSecurity.authorizeRequests().anyRequest().permitAll();
//
//        //未授权时访问须授权的资源端点
//        /*httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);*/
//
//        //放开一些接口的权限校验
//        /*for (String notAuthResource : SpringSecurityConstant.NONE_SECURITY_URL_PATTERNS) {
//            httpSecurity.authorizeRequests().antMatchers(notAuthResource).permitAll();
//        }*/
//
//        //其余的都需授权访问
//        /*httpSecurity.authorizeRequests().anyRequest().authenticated();*/
//
//        //前置token过滤器
//        /*httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);*/
//
//        //用户详情service
//        /*httpSecurity.userDetailsService(authService);*/
//
//        //全局不创建session
//        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        //禁用页面缓存，返回的都是json
//        httpSecurity.headers()
//                .frameOptions().disable()
//                .cacheControl();
//    }
//}