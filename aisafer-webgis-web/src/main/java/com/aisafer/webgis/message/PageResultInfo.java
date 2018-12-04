package com.aisafer.webgis.message;

import java.io.Serializable;

/**
 * @Author:lixiayu
 * @Description:Layui分页返回数据封装
 * @Date: Created in  2018/4/4 0004 上午 11:47
 * @Modified By:
 */
public class PageResultInfo implements Serializable {
    //状态码
    private int code;
    //信息
    private String msg;
    //总条数
    private long count;

    //页条数
    private long pageSize;

    //数据
    private Object data;

    //指明Action跳转的是JSON响应数据
    private static String JSON = "json";


    public PageResultInfo(int code, String msg, int count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public PageResultInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public PageResultInfo() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
