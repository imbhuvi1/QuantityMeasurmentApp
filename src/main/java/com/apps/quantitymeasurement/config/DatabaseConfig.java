package com.apps.quantitymeasurement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;

// Removed manual DataSource config to allow Spring Boot's auto-configuration
// to correctly map spring.datasource.url to Hikari's jdbcUrl.
// @Configuration
public class DatabaseConfig {

    // @Bean
    // @ConfigurationProperties(prefix = "spring.datasource")
    // public DataSource dataSource() {
    //     return DataSourceBuilder.create().build();
    // }
}