package com.musinsa.searchdemo.bean;
import com.musinsa.searchdemo.service.ElasticSearchService;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchBeanService {

    @Value("#{'${elasticsearch.hosts}'.split(',')}")
    private List<String> hosts;

    @Value("${elasticsearch.port}")
    private int port;

    @Bean(destroyMethod = "close")
    public ElasticSearchService getRestClient() {

        List<HttpHost> hostList = new ArrayList<>();
        for(String host : hosts) {
            hostList.add(new HttpHost(host, port, "http"));
        }
        RestClientBuilder builder = RestClient.builder(hostList.toArray(new HttpHost[hostList.size()]));
        return new ElasticSearchService(builder);
    }
}