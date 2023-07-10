package com.evoke.example.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {
//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer customizeObjectMapper() {
//        return new Jackson2ObjectMapperBuilderCustomizer() {
//            @Override
//            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
//                jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
//                jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
//            }
//        };
//    }
//@Bean
//public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder mapperBuilder) {
//    return mapperBuilder.build().setSerializationInclusion(JsonInclude.Include.NON_NULL);
//}
}
