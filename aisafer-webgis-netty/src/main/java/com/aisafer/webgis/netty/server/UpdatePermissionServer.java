package com.aisafer.webgis.netty.server;

import com.aisafer.base.multiple.RedisMultipleFactory;
import com.aisafer.base.redis.IRedis;
import com.aisafer.base.redis.RedisTools;
import com.aisafer.psg.user.client.dto.OrgParentReq;
import com.aisafer.psg.user.client.dto.OrgParentRes;
import com.aisafer.psg.user.client.dto.UserReq;
import com.aisafer.psg.user.client.dto.UserRes;
import com.aisafer.psg.user.client.service.ROrganizationService;
import com.aisafer.psg.user.client.service.RUserService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
@Component
public class UpdatePermissionServer{

    /** 日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(UpdatePermissionServer.class);

    /** 操作user的dubbo对象 */
    @Resource
    private RUserService rUserService;

    /** 操作组织的dubbo对象 */
    @Resource
    private ROrganizationService rOrganizationService;

    /** 操作车辆的dubbo对象 */
    @Resource
    private RVehicleService rVehicleService;

    /** redis操作对象 */
   // @Autowired
    //private RedisTools redisTools;

    @Resource(name = "redisMultipleFactory")
    private RedisMultipleFactory redisTools;

    /**
     * 更新车辆权限
     *
     */
    public void flushVehiclePermision(String simNo){
        try {
            // 根据SIM卡号查询车辆
            VehicleDataRes vehicleBySimNo = findVehicleBySimNo(simNo);

            if(vehicleBySimNo == null) {
                LOGGER.error("未查询到sim卡号为： {}  的车辆",simNo);
            }
            // 刷新车辆基础数据缓存
            putVehicleData(vehicleBySimNo,null);
            // 刷新车辆用户权限缓存
            putSimUserId(vehicleBySimNo,null);
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 更新sim卡号与车辆权限
     *
     * @param vehicleBySimNo
     * @param simNo
     * @return
     */
    public List<Long> putSimUserId(VehicleDataRes vehicleBySimNo,String simNo) {
        List<Long> setUserIds = new ArrayList<>();
        try {
            if(simNo != null && !"".equals(simNo)) {
                LOGGER.info("更新车辆用户权限 sim卡号为 : {}",simNo);
                // 根据SIM卡号查询车辆
                VehicleDataReq vehicleDataReq = new VehicleDataReq();
                vehicleDataReq.setSimNo(simNo);

                try {
                    vehicleBySimNo = redisTools.get(RedisKeyPrefix.VEHICLE_DATA.getValue() + simNo, VehicleDataRes.class);
                }catch (Exception e) {
                    LOGGER.error("从redis获取车辆信息出错{} {}", simNo, e.getMessage());
                    // e.printStackTrace();
                }

                if(vehicleBySimNo == null) {
                    LOGGER.warn("查询到车辆信息为空 键为：{}",RedisKeyPrefix.VEHICLE_DATA.getValue() + simNo);
                    vehicleBySimNo = rVehicleService.getVehicleBySimNo(vehicleDataReq);
                }
            }

            if(vehicleBySimNo==null) {
                return setUserIds;
            }
            // 根据车队ID查询所有上级部门
            OrgParentReq orgParentReq = new OrgParentReq();
            orgParentReq.setOrgId(vehicleBySimNo.getDeptId());
            List<OrgParentRes> parentAll = rOrganizationService.findParentAll(orgParentReq);

            // 查询所有部门的所有用户
            UserReq userReq = new UserReq();
            for(OrgParentRes orgParentRes : parentAll) {
                userReq.setOrgId(orgParentRes.getOrgId());
                List<UserRes> userByOrgId = rUserService.findUserByOrgId(userReq);
                for(UserRes userRes : userByOrgId) {
                    setUserIds.add(userRes.getId());
                }
            }
            redisTools.put(RedisKeyPrefix.SIM_DATA.getValue() + simNo,setUserIds);
            LOGGER.info("更新车辆权限缓存成功 sim卡号为 ： {}  user数量为 ： {}",simNo == null?vehicleBySimNo.getSimNo():simNo,setUserIds.size());
            return setUserIds;
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("更新sim卡号与车辆权限失败；错误原因 ： {}",e.getMessage());
            return setUserIds;
        }
    }

    /**
     * 根据sim卡号刷新车辆基础数据缓存
     *
     * @param vehicleBySimNo
     * @param simNo
     * @return
     */
    public VehicleBaseData putVehicleData(VehicleDataRes vehicleBySimNo,String simNo) {
        try {
            if(simNo != null && !"".equals(simNo)) {
                vehicleBySimNo = findVehicleBySimNo(simNo);
                if(vehicleBySimNo == null)
                    return new VehicleBaseData();
            }

            VehicleBaseData vehicleBaseData = new VehicleBaseData();
            vehicleBaseData.setPlateNo(vehicleBySimNo.getPlateNo());
            vehicleBaseData.setVehicleId(vehicleBySimNo.getVehicleId());

            // redisTools.put(MNettyHandler.VEHICLE_DATA + simNo,vehicleBaseData);
            LOGGER.info("更新车辆基础数据缓存成功 sim卡号为 ： {}  车牌号为 ： {}",simNo == null?vehicleBySimNo.getSimNo():simNo,vehicleBySimNo.getPlateNo());
            return vehicleBaseData;
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("putVehicleData 出错  错误原因： {}",e.getMessage());
            return null;
        }
    }

    /**
     * 根据Sim卡号获取车辆数据
     *
     * @param simNo
     * @return
     */
    public VehicleDataRes findVehicleBySimNo(String simNo) {
        try {
            if(simNo != null && !"".equals(simNo)) {
                // LOGGER.info("查询车辆基础数据 sim卡号为 : {}",simNo);
                // 根据SIM卡号查询车辆

                VehicleDataRes vehicleDataRes = null;
                try {
                    vehicleDataRes = redisTools.get(RedisKeyPrefix.VEHICLE_DATA.getValue() + simNo, VehicleDataRes.class);
                }catch (Exception e) {
                    e.printStackTrace();
                }

                if(vehicleDataRes == null) {
                    LOGGER.warn("查询到车辆信息为空 键为：{}",RedisKeyPrefix.VEHICLE_DATA.getValue() + simNo);
                    VehicleDataReq vehicleDataReq = new VehicleDataReq();
                    vehicleDataReq.setSimNo(simNo);
                    vehicleDataRes = rVehicleService.getVehicleBySimNo(vehicleDataReq);
                }

                return vehicleDataRes;
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("findVehicleBySimNo 出错  错误原因： {}",e.getMessage());
        }
        return null;
    }

}
