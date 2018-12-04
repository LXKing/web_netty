package com.aisafer.webgis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 报警事件消息体
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-27 14:15:24
 * @Modified By:
 */
public class AlarmModel implements Serializable  {

    /** sim卡号 */
    private String simNo;

    /** 报警事件名称 */
    private String alarmTypeName;

    /** 车牌号 */
    private String plateNo;

    /** 车辆ID */
    private String vehicleId;

    /** 报警时间 */
    private Date sendTime;

    /** 方向 */
    private Integer direction;

    /** 纬度 */
    private String latitude;

    /** 经度 */
    private String longitude;

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public String getAlarmTypeName() {
        return alarmTypeName;
    }

    public void setAlarmTypeName(String alarmTypeName) {
        this.alarmTypeName = alarmTypeName;
    }

}
