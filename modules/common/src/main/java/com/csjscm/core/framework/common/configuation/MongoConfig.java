package com.csjscm.core.framework.common.configuation;

import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.net.UnknownHostException;

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String MONGO_URI;
    @Value("${spring.data.mongodb.ehsy.uri}")
    private String MONGO_URI_EHSY;

    /******************************主mongodb配置***********************************/
    @Bean
    @Primary
    public MongoMappingContext mongoMappingContext() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        return mappingContext;
    }
    @Bean
    @Primary public MongoDbFactory dbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClientURI(MONGO_URI)); }

    /**
     * 使用自定义的typeMapper去除写入mongodb时的“_class”字段
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Primary public MappingMongoConverter mappingMongoConverter() throws Exception {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.dbFactory());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
    @Bean
    @Primary
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(dbFactory(), this.mappingMongoConverter()); }



    /******************************第二mongodb配置***********************************/
    @Bean
    public MongoMappingContext mongoMappingContext1() {
        MongoMappingContext mappingContext = new MongoMappingContext();
        return mappingContext;
    }
    @Bean
    public MongoDbFactory dbFactory1() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClientURI(MONGO_URI_EHSY)); }

    /**
     * 使用自定义的typeMapper去除写入mongodb时的“_class”字段
     *
     * @return
     * @throws Exception
     */
    @Bean
    public MappingMongoConverter mappingMongoConverter1() throws Exception {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.dbFactory1());
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext1());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
    @Bean
    public MongoTemplate mongoTemplate_ehsy() throws Exception {
        return new MongoTemplate(dbFactory1(), this.mappingMongoConverter1()); }


}
