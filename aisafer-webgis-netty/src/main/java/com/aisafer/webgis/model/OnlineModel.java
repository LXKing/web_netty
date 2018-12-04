package com.aisafer.webgis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 上下线消息实体类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-27 11:36:31
 * @Modified By:
 */
public class OnlineModel implements Serializable {

    /** sim卡号 */
    private String simNo;

    /** 上下线状态 */
    private Integer status;

    /** 车牌号 */
    private String plateNo;

    /** 车辆ID */
    private String vehicleId;

    /** 上下线时间 */
    private Date sendTime;

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

}
