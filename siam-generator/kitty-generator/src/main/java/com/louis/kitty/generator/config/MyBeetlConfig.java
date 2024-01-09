package com.louis.kitty.generator.config;

import org.beetl.core.GroupTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ibeetl.starter.BeetlTemplateCustomize;

@Configuration
public class MyBeetlConfig {
	
  @Bean
  public BeetlTemplateCustomize beetlTemplateCustomize(){
    return new BeetlTemplateCustomize(){
      public void customize(GroupTemplate groupTemplate){

      }
    };
  }
}