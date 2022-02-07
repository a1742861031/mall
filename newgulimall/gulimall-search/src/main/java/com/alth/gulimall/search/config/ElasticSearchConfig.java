package com.alth.gulimall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description es配置类
 * @Date 2022/2/6 12:17 PM
 * @Created by bobo
 */
@Configuration
public class ElasticSearchConfig {
    @Bean
    public RestHighLevelClient esRestClient() {
        return new RestHighLevelClient(RestClient.builder(
                new HttpHost("1.117.152.124", 9200, "http")
        ));
    }
}
