package com.aisafer.webgis.message;

import java.io.Serializable;

/**
 * 功能
 *
 * @Auth 2018-10-26 20:00:10
 *
 */
public class  JsonMessage implements Serializable {

    //对于用户操作的正常和成功的信息
    private boolean success;
    //发送给前端，用于显示的成功或失败的消息
    private String message;
    //数据
    private Object data;

    //指明Action跳转的是JSON响应数据
    private static String JSON = "json";

    public JsonMessage(boolean _success,String msg,Object data){
        this.success = _success;
        this.message = msg;
        this.data = data;
    }

    public JsonMessage(boolean _success,String msg){
        this.success = _success;
        this.message = msg;
    }
    public JsonMessage(){
        super();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
