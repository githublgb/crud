package com.lgb.bootweb.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.lgb.bootweb.common.MyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.security.auth.login.LoginContext;
import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mvc的自动配置,boot启动时会
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfig.class);

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("MyFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * boot启动时会使用此方法
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //1、定义一个convert转换消息的对象
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        //2、添加fastjson的配置信息  必须在pom.xml引入fastjson的jar包，并且版必须大于1.2.10
        FastJsonConfig config = new FastJsonConfig();

        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
                //  是否输出为null的字段,若为null 则显示该字段
                SerializerFeature.WriteMapNullValue,

                //  字符类型字段如果为null,输出为"",而非null
                // SerializerFeature.WriteNullStringAsEmpty,

                //  数值字段如果为null，则输出为0
                SerializerFeature.WriteNullNumberAsZero,

                //   List字段如果为null,输出为[],而非null
                //SerializerFeature.WriteNullListAsEmpty,

                //  Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse,
                //  输出key是包含双引号
                //SerializerFeature.QuoteFieldNames

                //等。。。
                //  循环引用 ,不懂什么意思？？？？
                //SerializerFeature.DisableCircularReferenceDetect,
        };

        // 中文乱码解决方案
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);//设定json格式且编码为UTF-8
        converter.setSupportedMediaTypes(mediaTypes);



        config.setSerializerFeatures(serializerFeatures);
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converters.add(converter);


        LOGGER.info("configureMessageConverters这个方法有没有执行=======================");
    }


    @Bean
    public MultipartConfigElement multipartConfigElement()
    {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        return factory.createMultipartConfig();
    }

    /**
     * 不明白这个是干什么 的
     *
     * @return
     */
    // @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        LOGGER.info("responseBodyConverter这个方法有没有执行=======================");
        StringHttpMessageConverter converter = new StringHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(new MediaType[]{new MediaType("text", "plain", Charset.forName("UTF-8"))}));
        return converter;
    }


}
