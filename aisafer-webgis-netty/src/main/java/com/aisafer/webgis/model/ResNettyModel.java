package com.aisafer.webgis.model;

import java.io.Serializable;

/**
 * netty发送消息实体类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-27 11:34:03
 * @Modified By:
 */
public class ResNettyModel<T> implements Serializable {

    /** 响应结果编码 */
    private Integer msgCode;

    /** 消息状态码 */
    private Boolean status;

    /** 响应结果消息 */
    private String message;

    private T content;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
