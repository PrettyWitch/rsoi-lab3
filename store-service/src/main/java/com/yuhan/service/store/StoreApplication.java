package com.yuhan.service.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yuhan
 * @date 10.11.2020 - 13:50
 * @purpose
 */
@SpringCloudApplication
@EnableFeignClients // 开启feign客户端
@EnableTransactionManagement
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }
}
