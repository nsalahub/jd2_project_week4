package com.gmail.salahub.nikolay.springbootmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages= {"com.gmail.salahub.nikolay.service",
        "com.gmail.salahub.nikolay.repository",
        "com.gmail.salahub.nikolay.springbootmodule"},
        exclude = UserDetailsServiceAutoConfiguration.class)
public class JD2Application {

    public static void main(String[] args) {
        SpringApplication.run(JD2Application.class, args);
    }

}
