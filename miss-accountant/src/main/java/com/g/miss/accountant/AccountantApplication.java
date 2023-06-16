
package com.g.miss.accountant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.g.miss.accountant.dao")
public class AccountantApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AccountantApplication.class, args);
    }
}
