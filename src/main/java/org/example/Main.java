package org.example;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static Logger log = (Logger) LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        log.info("Application Started");
    }
}