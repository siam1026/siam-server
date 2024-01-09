package com.louis.kitty.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 启动器
 * @author Louis
 * @date Nov 9, 2018
 */
@SpringBootApplication(scanBasePackages={"com.louis"})
public class KittyGeneratorApplication  extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KittyGeneratorApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(KittyGeneratorApplication.class, args);
	}
}