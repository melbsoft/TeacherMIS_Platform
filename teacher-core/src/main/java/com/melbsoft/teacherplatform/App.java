package com.melbsoft.teacherplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class App {

    public static void main(String[] args) {
//        System.setProperty("javax.net.ssl.trustStore", "/usr/local/java/ssl/cacerts");
        System.setProperty("javax.net.ssl.trustStore", "d:/cacerts");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");
        ConfigurableApplicationContext app = SpringApplication.run(App.class, args);
        app.registerShutdownHook();
    }


}
