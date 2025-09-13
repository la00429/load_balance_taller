package org.example.load_balance_taller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class LoadBalanceTallerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadBalanceTallerApplication.class, args);
    }

}
