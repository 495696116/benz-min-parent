package com.benz.min.common.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;


/***
 * @ClassName RedisService
 * @Description  自定义api
 * @Date 2019/3/8 10:35
 * @Author qr
 * @Param
 * @return
 **/
@Service
public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

   /***
    * @ClassName RedisService
    * @Description 设置 key - value
    * @Date 2019/3/8 10:35
    * @Author qr
    * @Param [key, value]
    * @return void
    **/
    public void set(String key, Serializable value) {
        /*RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);*/
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value);
    }

  /***
   * @ClassName RedisService
   * @Description  set
   * @Date 2019/3/8 10:35
   * @Author qr
   * @Param [key, value, timeout]
   * @return void
   **/
    public void set(String key, Serializable value, long timeout) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        vo.set(key, value, timeout, TimeUnit.SECONDS);
    }

  /***
   * @ClassName RedisService
   * @Description 获取value object
   * @Date 2019/3/8 10:35
   * @Author qr
   * @Param [key, clazz]
   * @return T
   **/
    public <T extends Serializable> T getObject(String key, Class<T> clazz) {
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        JSONObject obj = (JSONObject) JSONObject.toJSON((vo.get(key)));
        return JSON.toJavaObject(obj, clazz);
    }


    /***
     * @ClassName RedisService
     * @Description  获取value String
     * @Date 2019/3/8 10:36
     * @Author qr
     * @Param [key]
     * @return java.lang.String
     **/
    public String getString(String key) {
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }


    /***
     * @ClassName RedisService
     * @Description 指定key是否存在
     * @Date 2019/3/8 10:46
     * @Author qr
     * @Param [key]
     * @return boolean
     **/
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

   /***
    * @ClassName RedisService
    * @Description 删除指定key
    * @Date 2019/3/8 10:47
    * @Author qr
    * @Param [key]
    * @return void
    **/
    public void del(String key) {
        redisTemplate.delete(key);
    }

}
