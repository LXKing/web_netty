package com.aisafer.webgis.kafka;

import com.aisafer.dpc.client.dto.base.T808Message;
import com.aisafer.dpc.client.dto.custom.AlarmInfo;
import com.aisafer.dpc.client.dto.custom.LocationBasicInfo;
import com.aisafer.dpc.client.dto.jt808.JT_0200;
import com.aisafer.webgis.model.AlarmModel;
import com.aisafer.webgis.model.MyMessageCode;
import com.aisafer.webgis.model.ResNettyModel;
import com.aisafer.webgis.model.VehicleBaseData;
import com.aisafer.webgis.netty.server.MyRedisServer;
import com.aisafer.webgis.utils.BaseUtils;
import com.aisafer.webgis.utils.ChannelConfigUtils;
import com.aisafer.webgis.utils.MyThreadUtils;
import com.alibaba.fastjson.JSON;
import com.easy.mq.annotation.KafkaListener;
import com.easy.mq.enums.ConsumeResultStatus;
import com.easy.mq.event.MQEvent;
import com.polaris.base.utils.date.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 操作车辆报警信息
 *
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
@Service
public class AlarmConsumer extends BaseUtils {

    /** 日志 */
    private static Logger logger = LoggerFactory.getLogger(AlarmConsumer.class);

    /** 操作redis服务对象 */
    @Resource
    private MyRedisServer myRedisServer;

    /** 车辆报警消息 */
    @Value("${msg.alarm}")
    private String MSG_ALARM;

    /**
     * 消费车辆报警信息
     *
     * @param message
     * @return
     */
    @KafkaListener(group = "$groups[webgis]", topic = "$topics[alarm]")
    public ConsumeResultStatus scoreMsg(MQEvent<T808Message> message) {
        logger.debug("AlarmConsumer.scoreMsg 参数为 ： {}",JSON.toJSONString(message));

        try {
            MyThreadUtils.getThreadPoolExecutor().execute(new AlarmRunnable(message));
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("车辆报警信息处理失败 错误原因 ： {}",e.getMessage());
        }finally {
            return ConsumeResultStatus.SUCCESS;
        }
    }


    class AlarmRunnable implements Runnable{

        private MQEvent<T808Message> message;

        public AlarmRunnable(MQEvent<T808Message> message) {
            this.message = message;
        }

        @Override
        public void run() {
            // 获取消息
            JT_0200 jT_0200 = (JT_0200)message.getContent().getMessageBody();
            List<AlarmInfo> alarmInfos = jT_0200.getAlarmInfos();
            LocationBasicInfo basicInfo = jT_0200.getBasicInfo();
            AlarmModel alarmModel = new AlarmModel();

            ResNettyModel<AlarmModel> resNettyModel = new ResNettyModel();
            VehicleBaseData vehicleDataBySimNo = myRedisServer.getVehicleDataBySimNo(message.getContent().getSimNo());
            for(AlarmInfo alarmInfo : alarmInfos) {
                alarmModel.setSimNo(message.getContent().getSimNo());
                alarmModel.setPlateNo(vehicleDataBySimNo.getPlateNo());
                alarmModel.setVehicleId(String.valueOf(vehicleDataBySimNo.getVehicleId()));
                alarmModel.setAlarmTypeName(getAlarmTypeNameById(alarmInfo.getAlarmCode()));
                alarmModel.setSendTime(message.getContent().getTerminalSendTime());
                alarmModel.setLatitude(String.valueOf(basicInfo.getLatitude()/1000000.000000));
                alarmModel.setLongitude(String.valueOf(basicInfo.getLongitude()/1000000.000000));
                alarmModel.setDirection(basicInfo.getDirection());

                resNettyModel.setContent(alarmModel);
                resNettyModel.setMessage(MSG_ALARM);
                resNettyModel.setMsgCode(MyMessageCode.CODE_ALARM);
                resNettyModel.setStatus(true);

                // 根据sim卡号获取所有用户 循环发送消息
                List userIds = myRedisServer.getUsersBySimNo(message.getContent().getSimNo());
                for(Object userId : userIds) {
                    ChannelConfigUtils.sendSocketMessages(Long.parseLong(userId.toString()),resNettyModel);
                }
            }
        }
    }

}
