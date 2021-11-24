package com.example.springbootredis2;

import org.redisson.api.RFuture;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class LuaController {

    @Autowired
    RedissonClient redissonClient;

    private final String LIMIT_LUA = "local times=redis.call('incr',KEYS[1])\n" +
            "if times==1 then\n" +
            "    redis.call('expire',KEYS[1],ARGV[1])\n" +
            "end\n" +
            "if times > tonumber(ARGV[2]) then\n" +
            "    return 0\n" +
            "end \n" +
            "return 1";


    @GetMapping("/lua/{id}")
    public String lua(@PathVariable("id") Integer id) throws ExecutionException, InterruptedException {
        RScript rScript = redissonClient.getScript();
        List<Object> keys = Arrays.asList("LIMIT:" + id);
        RFuture<Object> future = rScript.evalAsync(RScript.Mode.READ_WRITE, LIMIT_LUA, RScript.ReturnType.INTEGER, keys, 10, 3);
        return future.get().toString();
    }
}
