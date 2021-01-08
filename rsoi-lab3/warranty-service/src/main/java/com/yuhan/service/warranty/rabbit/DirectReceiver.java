package com.yuhan.service.warranty.rabbit;

import com.yuhan.service.warranty.model.ItemWarrantyRequest;
import com.yuhan.service.warranty.model.OrderWarrantyResponse;
import com.yuhan.service.warranty.model.RabbitWarrantyRequest;
import com.yuhan.service.warranty.service.WarrantyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yuhan
 * @date 03.01.2021 - 14:53
 * @purpose 创建消息接收监听类
 */
@Component
public class DirectReceiver {
    @Autowired
    WarrantyService warrantyService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
    @RabbitHandler
    public void process(RabbitWarrantyRequest testMessage) {
        int itemUid = testMessage.getOrderItemUid();
        ItemWarrantyRequest request = testMessage.getItemWarrantyRequest();
        logger.info("接收处理队列当中的消息： " + testMessage.toString());
        System.out.println("DirectReceiver消费者收到消息  : " + testMessage.toString());

        OrderWarrantyResponse orderWarrantyResponse = warrantyService.warrantyRequest(itemUid, request);
        logger.info("处理结果 " + orderWarrantyResponse.toString());
        System.out.println("DirectReceiver消费者处理结果  : " + orderWarrantyResponse.toString());
    }

}
