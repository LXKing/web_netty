package com.aisafer.webgis.utils;

import com.aisafer.psg.pcs.client.dto.AlarmDataRes;
import com.aisafer.psg.pcs.client.service.RAlarmItemService;
import com.aisafer.webgis.utils.mapUtils.Constants;
import com.aisafer.webgis.utils.mapUtils.MapFixService;
import com.aisafer.webgis.utils.mapUtils.PointLatLng;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 操作dubbo接口查询类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-27 17:23:17
 * @Modified By:
 */
@Component
public class BaseUtils {

    /** 操作报警类型 */
    @Resource
    protected RAlarmItemService rAlarmItemService;

    /**
     * 根据ID查询报警名称
     *
     * @param alarmType
     * @return
     */
    protected String getAlarmTypeNameById(Integer alarmType) {
        List<Integer> paramList = new ArrayList<>();
        paramList.add(alarmType);

        Map<Integer, AlarmDataRes> alarmDatas = rAlarmItemService.getAlarmDatas(paramList);
        if(alarmDatas != null && alarmDatas.containsKey(alarmType)) {
            AlarmDataRes alarmDataRes = alarmDatas.get(alarmType);
            return alarmDataRes.getAlarmName();
        }
        return "";
    }

    /**
     * 转换经纬度
     *
     * @param latParam
     * @param lngParam
     * @return
     */
    protected PointLatLng changeLatLng(Integer latParam, Integer lngParam) {
        double lat = Double.valueOf(latParam.toString());
        double lng = Double.valueOf(lngParam.toString());

        try {
            PointLatLng fix = MapFixService.fix(lat/1000000, lng/1000000, Constants.MAP_BAIDU);
            return fix;
        }catch (Exception e) {
            e.printStackTrace();
            return new PointLatLng();
        }
    }

}
