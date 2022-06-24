package com.znsd.oneself;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages={"com.znsd.oneself.*"})
public class OneselfApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneselfApplication.class, args);
    }

}
