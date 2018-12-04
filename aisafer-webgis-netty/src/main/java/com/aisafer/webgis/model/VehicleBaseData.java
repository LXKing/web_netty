package com.aisafer.webgis.model;

import java.io.Serializable;

/**
 * 车辆属性简单封装类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-29 10:53:45
 * @Modified By:
 */
public class VehicleBaseData implements Serializable {

    private Long vehicleId;

    private String plateNo;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }


}
