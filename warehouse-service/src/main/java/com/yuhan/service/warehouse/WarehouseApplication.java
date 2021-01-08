package com.yuhan.service.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yuhan
 * @date 11.11.2020 - 15:14
 * @purpose
 */
@SpringCloudApplication //代替上面三个注解的组合注解
@EnableFeignClients // 开启feign客户端
@EnableTransactionManagement
public class WarehouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}
