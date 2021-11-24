package com.example.springbootredis2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisTemplateUtils {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * String 操作
     */

    //   设置当前的key以及value值
    public void setKeyValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    //   在原有的值基础上新增字符串到末尾
    public void appendValue(String key, String value) {
        redisTemplate.opsForValue().append(key, value);
    }

    //    重命名
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    //   批量获取值
    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }

    //    返回key中字符串的子字符
    public String getCharacterRange(String key, long start, long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    //   将旧的key设置为value，并且返回旧的key
    public Object setKeyAsValue(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    //   返回传入key所存储的值的类型
    public DataType getKeyType(String key) {
        return redisTemplate.type(key);
    }

    //   如果旧值存在时，将旧值改为新值
    public Boolean renameOldKeyIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    //    删除key
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    //    删除多个key
    public void deleteKey(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
    }

    //    指定key的失效时间
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.MINUTES);
    }

    //    根据key获取过期时间
    public long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    //    判断key是否存在
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    //   将当前数据库的key移动到指定redis中数据库当中
    public Boolean moveToDbIndex(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }


    /**
     * Hash 操作
     */

    //  新增hashMap值
    public void addHash(String key, Object hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    //  以map集合的形式添加键值对
    public void hPutAll(String key, Map<String, String> maps) {
        redisTemplate.opsForHash().putAll(key, maps);
    }

    //  仅当hashKey不存在时才设置
    public Boolean hashPutIfAbsent(String key, String hashKey, String value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    //  删除一个或者多个hash表字段
    public Long hashDelete(String key, Object... fields) {
        return redisTemplate.opsForHash().delete(key, fields);
    }

    //  查看hash表中指定字段是否存在
    public boolean hashExists(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    //  获取hash表中存在的所有的值
    public List<Object> hValues(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    //  给哈希表key中的指定字段的整数值加上增量increment
    public Long hashIncrBy(String key, Object field, long increment) {
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    public Double hIncrByDouble(String key, Object field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    //   获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null。
    public Object getHashByMap(String key, Object field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    //   获取变量中的键值对
    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    //  获取所有hash表中字段
    public Set<Object> getAllKey(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    //  获取hash表中字段的数量
    public Long getKeySize(String key) {
        return redisTemplate.opsForHash().size(key);
    }


    /**
     * list 操作
     */

    /**
     * set 操作
     */

    /**
     * zset 操作
     */


}
