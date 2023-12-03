package com.siam.system.config;

import com.siam.package_common.converter.DateConverter;
import com.siam.system.interceptor.AdminInterceptor;
import com.siam.system.interceptor.MemberInterceptor;
import com.siam.system.interceptor.MerchantInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * 拦截器配置类
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private MemberInterceptor memberInterceptor;

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private MerchantInterceptor merchantInterceptor;

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 重写添加拦截器方法并添加配置拦截器
     *
     * @param registry
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册管理员拦截器
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/rest/admin/**")
                .excludePathPatterns("/rest/admin/login")
                .excludePathPatterns("/rest/admin/loginByMobile")
                .excludePathPatterns("/rest/admin/forgetPassword/step1")
                .excludePathPatterns("/rest/admin/forgetPassword/step2");

        //注册用户拦截器
        registry.addInterceptor(memberInterceptor)
                .addPathPatterns("/rest/member/**")
                .excludePathPatterns("/rest/member/login")
                .excludePathPatterns("/rest/member/loginByMobile")
                /*.excludePathPatterns("/rest/member/logout")*/
                .excludePathPatterns("/rest/member/register")
                .excludePathPatterns("/rest/member/verification/login")
                .excludePathPatterns("/rest/member/weChat/login")
                .excludePathPatterns("/rest/member/wxPay/notify")
                .excludePathPatterns("/rest/member/wxPay/refundSuccessNotify");

        //商户拦截器
        registry.addInterceptor(merchantInterceptor)
//                .addPathPatterns("/api-member/rest/member/**")
                .addPathPatterns("/*/rest/merchant/**")
                .excludePathPatterns("/rest/merchant/login")
                .excludePathPatterns("/rest/merchant/loginByMobile")
                .excludePathPatterns("/rest/merchant/logout")
                .excludePathPatterns("/rest/merchant/register")
                .excludePathPatterns("/rest/merchant/registerByMobile")
                .excludePathPatterns("/rest/merchant/verification/login")
                .excludePathPatterns("/rest/merchant/forgetPassword/step1");
    }

    /**
     * 改用过滤器CorsFilter 来配置跨域，由于Filter的位置是在Interceptor之前的，问题得到解决
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 设置允许跨域请求的域名
        config.addAllowedOrigin("*");
        // 是否允许证书(这个不开启，请求会报错的)
        config.setAllowCredentials(true);
        // 设置允许的方法
        config.addAllowedMethod("*");
        // 允许任何头
        config.addAllowedHeader("*");
        config.addExposedHeader("token");
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }

    /**
     * 该方法已废弃
     * @Deprecated
     *
     * 浏览器跨域处理（只会在spring初始化的时候加载一次）
     * 注意：这种跨域问题的处理方式 对于拦截器不放行的请求无效
     *
     * @param registry
     **/
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.SET_COOKIE).maxAge(3600L);
    }*/

    /**
     * 配置全局日期转换器
     */
    @PostConstruct
    public void initEditableAvlidation() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if(initializer.getConversionService()!=null) {
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            genericConversionService.addConverter(new DateConverter());//添加自定义的类型转换器
        }
    }
}