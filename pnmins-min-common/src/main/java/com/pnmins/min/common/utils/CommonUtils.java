package com.pnmins.min.common.utils;

import com.pnmins.min.bean.vo.CiLogVo;
import org.slf4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CommonUtils
 * @Description 工具类
 * @Author qr
 * @Date 2019/3/7 16:09
 **/
public class CommonUtils {

    /***
     * @ClassName CommonUtils
     * @Description 打日志log
     * @Date 2019/3/8 10:51
     * @Author qr
     * @Param [logger, req, res]
     * @return void
     **/
    public static void doLog(Logger logger, Object req, Object res){
        CiLogVo logvo=new CiLogVo();
        Map<String, Object> reqcontent=new HashMap<String, Object>();
        Map<String, Object> rescontent=new HashMap<String, Object>();
        reqcontent.put("入参详细", req);
        logvo.setRequestContent(reqcontent);
        logvo.setRequestDate(DateUtil.dateToString(new Date(), DateUtil.DATE_FORMAT_FIVE));
        //开始执行方法
        rescontent.put("返回参数",  res);
        logvo.setResponseContent(rescontent);
        logvo.setResponseDate(DateUtil.dateToString(new Date(), DateUtil.DATE_FORMAT_FIVE));
        //开始打印
        logger.info(GsonUtils.toJson(logvo));
    }

}
