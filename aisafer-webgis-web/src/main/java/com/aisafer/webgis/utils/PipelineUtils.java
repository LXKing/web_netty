package com.aisafer.webgis.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

@Component
public class PipelineUtils {

    @Value("${spring.redis2.host}")
    private String host;
    @Value("${spring.redis2.port}")
    private int port;
    @Value("${spring.redis2.password}")
    private String password;
    @Value("${spring.redis.timeout}")
    private int timeout;

    public Pipeline getPipeline(){
        JedisPoolConfig config = new JedisPoolConfig();
        JedisPool pool = new JedisPool(config, host, port,timeout,password);
        Jedis jedis = pool.getResource();
        Pipeline pipeline = jedis.pipelined();
        return pipeline;
    }
}
