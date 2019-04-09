package com.pnmins.min.mapper;


import com.pnmins.min.bean.vo.userInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserInfoDao {
    List<userInfo> queryPH();
}
