package com.csjscm.core.framework.elasticsearch.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class EsConfig {
    @Value("${es.host}")
    private String host;
    @Value("${es.port}")
    private int port;
    private String schema="http";
    @Value("${es.connectTimeOut}")
    private int connectTimeOut;
    @Value("${es.socketTimeOut}")
    private int socketTimeOut;
    @Value("${es.connectionRequestTimeOut}")
    private int connectionRequestTimeOut;

    @Value("${es.maxConnectNum}")
    private int maxConnectNum;
    @Value("${es.maxConnectPerRoute}")
    private int maxConnectPerRoute;

    private boolean uniqueConnectTimeConfig = true;
    private boolean uniqueConnectNumConfig = true;
    private RestClientBuilder builder;
    private RestHighLevelClient client;

    @Bean
    public RestHighLevelClient client(){
        builder=RestClient.builder(new HttpHost(host,port,schema));
        if(uniqueConnectTimeConfig){
            setConnectTimeOutConfig();
        }
        if(uniqueConnectNumConfig){
            setMutiConnectConfig();
        }
        client=new RestHighLevelClient(builder);
        return client;
    }

    // 主要关于异步httpclient的连接延时配置
     public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public Builder customizeRequestConfig(Builder requestConfigBuilder) {
                requestConfigBuilder.setConnectTimeout(connectTimeOut);
                requestConfigBuilder.setSocketTimeout(socketTimeOut);
                requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
                return requestConfigBuilder;
            }
        });
    }

    // 主要关于异步httpclient的连接数配置
     public void setMutiConnectConfig() {
        builder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                httpClientBuilder.setMaxConnTotal(maxConnectNum);
                httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
                return httpClientBuilder;
            }
        });
    }

    public void close() {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }
}
