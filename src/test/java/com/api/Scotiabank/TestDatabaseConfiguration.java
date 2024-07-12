package com.api.Scotiabank;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDatabaseConfiguration {

    @Bean
    ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:h2:mem:///school-h2");
    }
}
