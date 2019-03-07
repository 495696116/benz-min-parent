package com.pnmins.min.ws.controller;

import com.pnmins.min.bean.vo.userInfo;
import com.pnmins.min.common.utils.CommonUtils;
import com.pnmins.min.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class proController {


    @Autowired
    private UserInfoService UserInfoService;


//    private final static Logger performanceLogger = LoggerFactory.getLogger("performanceLogger");
    private final static Logger qiaoLogger = LoggerFactory.getLogger("qiaoLogger");


    @GetMapping("/ph")
    public String getMessagee() {
        List<userInfo> list = UserInfoService.queryPH();
        StringBuffer sb = new StringBuffer();
        list.forEach(m -> {
            sb.append(m.getName());
        });
        String uuid = UUID.randomUUID().toString();
        userInfo u = new userInfo();
        u.setId(uuid);
        u.setName("ph 队列1");
        Date stDate = new Date();
        CommonUtils.doLog(qiaoLogger,stDate,list.get(0),list.get(2));
        return "提供服务:center" + sb.toString();
    }


}
