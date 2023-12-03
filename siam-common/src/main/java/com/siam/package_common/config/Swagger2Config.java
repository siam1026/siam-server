//package com.siam.package_common.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Swagger2接口文档配置类
// **/
//@Configuration
//@EnableSwagger2
//public class Swagger2Config {
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.siam"))//扫描包路径
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    //构建api文档的详细信息函数,注意这里的注解引用的是哪个
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                //页面标题
//                .title("暹罗外卖接口文档")
//                //描述
//                .description("")
//                //版本号
//                .version("1.0")
//                .build();
//    }
//}