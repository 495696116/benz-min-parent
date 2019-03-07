package com.pnmins.min.bean.vo;

import java.util.Map;
/***
 * @Author qr
 * @Description 日志实体类
 * @Date  16:12
 **/
public class CiLogVo {

    private Map<String, Object> requestContent;
    private Map<String, Object> responseContent;
    private String requestDate;
    private String responseDate;

    public Map<String, Object> getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(Map<String, Object> requestContent) {
        this.requestContent = requestContent;
    }

    public Map<String, Object> getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(Map<String, Object> responseContent) {
        this.responseContent = responseContent;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }


}
