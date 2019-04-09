package com.pnmins.min.service.impl;

import com.pnmins.min.bean.vo.userInfo;
import com.pnmins.min.mapper.UserInfoDao;
import com.pnmins.min.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @ClassName UserInfoServiceImpl
 * @Description 测试 111
 * @Date 2019/3/7 18:54
 * @Author qr
 * @Param
 * @return
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    private UserInfoDao userInfoDao;


    @Override
    public List<userInfo> queryPH() {
        return userInfoDao.queryPH();
    }


}
