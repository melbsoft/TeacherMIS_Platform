package com.melbsoft.teacherplatform.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
@Slf4j
public class JsonParserConfig {
    private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    @Primary
    public ObjectMapper jsonMapper() {
        ObjectMapper jsonMapper =
                Jackson2ObjectMapperBuilder.json().simpleDateFormat(dateTimeFormat)
                        .timeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")))
                        .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
                        .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                        .serializers(new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                        .serializers(InstantSerializer.INSTANCE)
                        .serializers(OffsetDateTimeSerializer.INSTANCE)
                        .findModulesViaServiceLoader(true).build();

        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jsonMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        jsonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jsonMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);

//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        jsonMapper.registerModule(javaTimeModule);
        return jsonMapper;
    }
}
