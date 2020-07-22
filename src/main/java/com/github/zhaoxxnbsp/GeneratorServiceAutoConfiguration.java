package com.github.zhaoxxnbsp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@EnableConfigurationProperties(GeneratorProperties.class)
public class GeneratorServiceAutoConfiguration {

    @Autowired
    GeneratorProperties generatorProperties;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public GeneratorService generatorService(){
        return new GeneratorService(generatorProperties, jdbcTemplate);
    }
}
