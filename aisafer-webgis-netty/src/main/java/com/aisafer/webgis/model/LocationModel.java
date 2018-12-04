package com.aisafer.webgis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 位置信息参数
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-29 15:42:42
 * @Modified By:
 */
public class LocationModel implements Serializable {

    /** sim卡号 */
    private String simNo;

    /** 车牌号 */
    private String plateNo;

    /** 车辆ID */
    private String vehicleId;

    /** 纬度 */
    private String latitude;

    /** 经度 */
    private String longitude;

    /** 速度 */
    private String speed;

    /** 方向 */
    private Integer direction;

    /** 位置上传时间 */
    private Date sendTime;

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
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

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
