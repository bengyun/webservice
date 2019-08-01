package com.bengyun.webservice.tool;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxDbConfig {

    @Value("${influx.url:''}")
    private String influxDBUrl;

    @Value("${influx.user:''}")
    private String userName;

    @Value("${influx.password:''}")
    private String password;

    @Value("${influx.database:''}")
    private String database;

    @Bean
    public InfluxQLUtils influxQLUtils() {
        return new InfluxQLUtils(userName, password, influxDBUrl, database, "");
    }
    
    @Bean
    public FluxScriptUtils fluxScriptUtils() {
        return new FluxScriptUtils();
    }
}
