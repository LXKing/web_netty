package com.aisafer.webgis.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.polaris.base.utils.map.MapUtils;
import com.polaris.base.utils.string.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统测试类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-22 15:16:43
 * @Modified By:
 */
@Controller
public class TestMonitor {

    @RequestMapping("findTestMonitor")
    public String fimdStr() {
        return "ftl/testMonitor";
    }

    @RequestMapping("testRedis")
    @ResponseBody
    public String testRedis(){
        JedisPoolConfig config = new JedisPoolConfig();
        JedisPool pool = new JedisPool(config, "192.168.200.52", 6379,360000,"123456");
        Jedis jedis = pool.getResource();
        Pipeline pipelined = jedis.pipelined();
        String key = "Vehicle_Data_20000003131";
        Response<String> stringResponse = pipelined.get(key);
        pipelined.sync();
        String s = stringResponse.get();
        Map maps = (Map) JSON.parse(s);
        if(maps.get("vehicleId") instanceof Long) {

        }else if(maps.get("vehicleId") instanceof Integer) {

        }
        Integer id = (Integer) maps.get("vehicleId");
        String plateNo = (String) maps.get("plateNo");
        return s;
    }

}
