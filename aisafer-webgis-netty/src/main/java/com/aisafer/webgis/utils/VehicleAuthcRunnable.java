package com.aisafer.webgis.utils;

import com.aisafer.psg.vehicle.client.dto.VehicleDataRes;
import com.aisafer.webgis.netty.server.UpdatePermissionServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 更新车辆权限线程
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-11-05 11:58:34
 * @Modified By:
 */
public class VehicleAuthcRunnable implements Runnable {

    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(VehicleAuthcRunnable.class);

    /** 操作车辆的dubbo对象 */
    private UpdatePermissionServer updatePermissionServer;

    private VehicleDataRes vehicleBySimNo;

    /** sim卡号 */
    private String simNo;

    public VehicleAuthcRunnable(String simNo) {
        this.simNo = simNo;
    }

    public VehicleAuthcRunnable() {
        super();
    }

    @Override
    public void run() {
        logger.info("异步刷新车辆权限和车辆基本信息");
        this.updatePermissionServer = SpringContextUtil.getInstanceBean("updatePermissionServer", UpdatePermissionServer.class);
        vehicleBySimNo = this.updatePermissionServer.findVehicleBySimNo(simNo);
        this.updatePermissionServer.flushVehiclePermision(simNo);
    }

    public VehicleDataRes getVehicleBySimNo() {
        return vehicleBySimNo;
    }

    public void setVehicleBySimNo(VehicleDataRes vehicleBySimNo) {
        this.vehicleBySimNo = vehicleBySimNo;
    }

}
