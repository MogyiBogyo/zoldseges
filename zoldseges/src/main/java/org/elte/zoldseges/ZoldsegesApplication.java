package org.elte.zoldseges;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZoldsegesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZoldsegesApplication.class, args);
        System.out.println("Welcome little grocer");
    }

}
