package com.example.aston;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class AstonApplication {

    public static void main(String[] args) throws UnknownHostException {
        Environment env = SpringApplication.run(AstonApplication.class, args).getEnvironment();
        log.info(
                """
                                        
                        ----------------------------------------------------------
                        \tApplication '{}' is running! Access URLs:
                        \tLocal: \t\thttp://127.0.0.1:{}
                        \tExternal: \thttp://{}:{}
                        \tMessage: \t{}
                        ----------------------------------------------------------
                        """,
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("spring.message")
        );
    }
}
