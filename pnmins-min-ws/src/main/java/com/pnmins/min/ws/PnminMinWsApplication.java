package com.pnmins.min.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.pnmins.min.*"})
public class PnminMinWsApplication {


    public static void main(String[] args) {
        SpringApplication.run(PnminMinWsApplication.class, args);
    }

}

