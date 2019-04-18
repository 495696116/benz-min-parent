package com.benz.min.ws.controller;

import com.benz.min.bean.vo.userInfo;
import com.benz.min.common.redis.RedisService;
import com.benz.min.common.utils.CommonUtils;
import com.benz.min.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class proController {


    //查库
    @Autowired
    private UserInfoService UserInfoService;


    //缓存
    @Autowired
    private RedisService redis;

//    private final static Logger performanceLogger = LoggerFactory.getLogger("performanceLogger");


    //log.xml 配置的日志类
    private final static Logger qiaoLogger = LoggerFactory.getLogger("qiaoLogger");


    @GetMapping("/ph")
    public String getMessagee() {

        //缓存
        //测试redis 存放object
        userInfo user = new userInfo();
        user.setName("a1");
        user.setId("ADSADSA65");
        redis.set("user1", user, 1000);
        userInfo info = redis.getObject("user1", userInfo.class);
        System.out.println(info.getName());
        System.out.println(info);

        //测试redis 存放 String
        redis.set("q", "123", 1000);
        String v = redis.getString("q");
        System.out.println(v);


        //查库
        List<userInfo> list = UserInfoService.queryPH();
        StringBuffer sb = new StringBuffer();
        list.forEach(m -> {
            sb.append(m.getName());
        });

        //uuid
        String uuid = UUID.randomUUID().toString();

        //实体类
        userInfo u = new userInfo();
        u.setId(uuid);
        u.setName("ph 队列1");

        //打印日志工具类，存放某一个日之类,时间,入参 ， 出参
        CommonUtils.doLog(qiaoLogger, "11111", "2222");
        return "提供服务:center" + sb.toString();
    }


    @GetMapping("/doText")
    @ResponseBody
    public String doText() {
        return "111111";
    }

}
