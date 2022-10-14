package dev.t1dmlgus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
public class OrderMvp2Application {
    public static void main(String[] args) {
        SpringApplication.run(OrderMvp2Application.class, args);
    }
}