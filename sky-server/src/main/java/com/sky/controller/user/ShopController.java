package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "营业状态相关接口")
@Slf4j
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("获取营业状态")
    public Result<Integer> getStatus(){
        Integer shopStatus = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("设置营业状态为:{}",shopStatus == 1 ? "营业中" : "打烊中");
        return Result.success(shopStatus);
    }
}
