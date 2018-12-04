package com.aisafer.webgis.controller.authc;

import com.aisafer.dpc.client.dto.*;
import com.aisafer.dpc.client.service.RLocationService;
import com.aisafer.dpc.client.service.RVehicleAlarmRecordService;
import com.aisafer.webgis.controller.BaseController;
import com.polaris.base.utils.string.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("authc")
public class WebgisAlarmController extends BaseController {
    /** 日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(WebgisAlarmController.class);

    @Autowired
    private RVehicleAlarmRecordService rVehicleAlarmRecordService;

    @Autowired
    private RLocationService rLocationService;

    @Controller
    @RequestMapping("authc")
    class view {
        @RequestMapping("/webgisAlarmList")
        public String webgisAlarmList(){
            return "ftl/webgisAlarmList";
        }

        @RequestMapping("/webgisRealList")
        public String webgisRealList(){
            return "ftl/webgisRealList";
        }
    }

    @ResponseBody
    @RequestMapping("/getWebgisAlarmList")
    public String getWebgisAlarmList(VehicleAlarmRecordReq vehicleAlarmRecordReq){
        LOGGER.info("Controller层报警数据查询条件："+JsonUtil.bean2Json(vehicleAlarmRecordReq));
        try {
            VehicleAlarmRecordResp vehicleAlarmRecordResp = rVehicleAlarmRecordService.QueryVehicleAlarmDetailList(vehicleAlarmRecordReq);
            Integer pageSize = vehicleAlarmRecordResp.getPageSize();
            Integer total = vehicleAlarmRecordResp.getTotal();
            List<VehicleAlarmRecord> list = vehicleAlarmRecordResp.getList();
            return setPageResultInfo(true,getString("msg.searchSuccess"),total,pageSize,list);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return setPageResultInfo(false,getString("msg.searchFaild"));
        }
    }

    @ResponseBody
    @RequestMapping("/getWebgisRealList")
    public String getWebgisRealList(LocationIndexByIdsReq locationReq){
        try {
            LocationInfoResp locationInfo = rLocationService.getLocationInfoByVehicle(locationReq);
            List<LocationInfo> list = locationInfo.getList();
            Integer pageSize = locationInfo.getPageSize();
            Integer total = locationInfo.getTotal();
            return setPageResultInfo(true,getString("msg.searchSuccess"),total,pageSize,list);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return setPageResultInfo(false,getString("msg.searchFaild"));
        }
    }

}
