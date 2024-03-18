package org.example;

import ch.qos.logback.classic.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static Logger log;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class);
    }
}