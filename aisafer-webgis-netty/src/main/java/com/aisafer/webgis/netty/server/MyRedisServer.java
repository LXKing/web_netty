package com.aisafer.webgis.netty.server;

import com.aisafer.base.multiple.RedisMultipleFactory;
import com.aisafer.base.redis.RedisTools;
import com.aisafer.psg.vehicle.client.dto.VehicleDataReq;
import com.aisafer.psg.vehicle.client.dto.VehicleDataRes;
import com.aisafer.psg.vehicle.client.model.RedisKeyPrefix;
import com.aisafer.psg.vehicle.client.service.RVehicleService;
import com.aisafer.webgis.model.MNettyHandler;
import com.aisafer.webgis.model.VehicleBaseData;
import com.polaris.base.utils.string.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * 提供操作redis服务
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-27 10:02:04
 * @Modified By:
 */
@Component
public class MyRedisServer {

    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(MyRedisServer.class);

    /** redis操作对象 */
   // @Autowired
   // private RedisTools redisTools;
    @Resource(name = "redisMultipleFactory")
    private RedisMultipleFactory redisTools;

    @Autowired
    private RVehicleService rVehicleService;

    /** 更新车辆权限的对象 */
    @Resource
    private UpdatePermissionServer updatePermissionServer;

    /**
     * 根据sim卡号获取具有权限的所有用户
     * 如果redis中没有  查询后返回
     *
     * @param simNo
     * @return
     */
    public List getUsersBySimNo(String simNo) {
        List userList = redisTools.get(RedisKeyPrefix.SIM_DATA.getValue() + simNo, List.class);

        if(userList != null && userList.size() > 0)
            return userList;

        logger.info("redis中没有sim卡号为 {} 的权限数据",simNo);
        return updatePermissionServer.putSimUserId(null,simNo);
    }

    /**
     * 根据sim卡号获取车辆基础数据
     * redis中没有  查询后返回
     *
     * @param simNo
     * @return
     */
    public VehicleBaseData getVehicleDataBySimNo(String simNo) {
        VehicleBaseData vehicleBaseData = new VehicleBaseData();

        try {
            VehicleDataRes vehicleDataRes = null;
            try {
                vehicleDataRes = redisTools.get(RedisKeyPrefix.VEHICLE_DATA.getValue() + simNo, VehicleDataRes.class);
                // logger.info("从redis获取车辆信息数据，key为 ： {}， 值为：{}",MNettyHandler.VEHICLE_DATA + simNo,JsonUtil.bean2Json(vehicleDataRes));
            }catch (Exception e) {
                logger.error("从redis获取车辆信息出错{} {}", simNo, e.getMessage());
                // e.printStackTrace();
            }

            if(vehicleDataRes == null || vehicleDataRes.getSimNo() == null || vehicleDataRes.getSimNo().equals("")) {
                logger.warn("从redis中未获取到key为：{}，的数据",RedisKeyPrefix.VEHICLE_DATA.getValue() + simNo);
                VehicleDataReq vehicleDataReq = new VehicleDataReq();
                vehicleDataReq.setSimNo(simNo);
                vehicleDataRes = rVehicleService.getVehicleBySimNo(vehicleDataReq);
                // redisTools.put(MNettyHandler.VEHICLE_DATA + simNo,vehicleDataRes);
            }
            if(vehicleDataRes != null){
                vehicleBaseData.setVehicleId(vehicleDataRes.getVehicleId());
                vehicleBaseData.setPlateNo(vehicleDataRes.getPlateNo());
            }
        }catch (Exception e){
            logger.error("获取车辆信息出错{} {}", simNo, e.getMessage());
            e.printStackTrace();
        }
        return vehicleBaseData;
    }

}
