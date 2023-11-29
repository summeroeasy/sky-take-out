package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单方法
     */
    @Scheduled(cron = "0 * * * * ? ") //每分钟触发一次
    public void processTimeOut() {
        log.info("定时处理超时订单: {}", LocalDateTime.now());
        List<Orders> orderList = orderMapper.getByStatusAndOrdertimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().plusHours(-15));
        if (orderList != null && orderList.size() > 0 ){
            for (Orders orders : orderList) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelTime(LocalDateTime.now());
                orders.setCancelReason("订单超时,自动取消");
                orderMapper.update(orders);
            }
        }
    }


    /**
     *
     * 一直处于派送中的订单
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天一点触发一次
    public void processDeliveryOrder(){
        log.info("一直派送中的订单: {}",LocalDateTime.now());
        List<Orders> orderList = orderMapper.getByStatusAndOrdertimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().plusDays(-1));
        if (orderList != null && orderList.size() > 0 ){
            for (Orders orders : orderList) {
                orders.setStatus(Orders.COMPLETED);
                orderMapper.update(orders);
            }
        }
    }
}
