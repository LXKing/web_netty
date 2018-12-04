package com.aisafer.webgis.kafka;

import com.aisafer.dpc.client.dto.base.T808Message;
import com.aisafer.dpc.client.dto.custom.LocationBasicInfo;
import com.aisafer.dpc.client.dto.jt808.JT_0200;
import com.aisafer.webgis.model.LocationModel;
import com.aisafer.webgis.model.MyMessageCode;
import com.aisafer.webgis.model.ResNettyModel;
import com.aisafer.webgis.model.VehicleBaseData;
import com.aisafer.webgis.netty.server.MyRedisServer;
import com.aisafer.webgis.utils.BaseUtils;
import com.aisafer.webgis.utils.ChannelConfigUtils;
import com.aisafer.webgis.utils.MyThreadUtils;
import com.easy.mq.annotation.KafkaListener;
import com.easy.mq.enums.ConsumeResultStatus;
import com.easy.mq.event.MQEvent;
import com.polaris.base.utils.string.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 处理车辆位置信息
 *
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
@Service
public class LocationConsumer extends BaseUtils {

	/** 日志 */
	private static Logger logger = LoggerFactory.getLogger(LocationConsumer.class);

	/** 操作redis服务对象 */
	@Resource
	private MyRedisServer myRedisServer;

	/** 车辆报警消息 */
	@Value("${msg.local}")
	private String MSG_LOCAL;

	/**
	 * 消费车辆gps信息
	 *
	 * @param message
	 * @return
	 */
	/*public ConsumeResultStatus msg(MQEvent<T808Message> message) {*/
	@KafkaListener(group = "$groups[webgis]", topic = "$topics[location]")
	public ConsumeResultStatus msg(List<MQEvent<T808Message>> message) {
		logger.info("批量消费位置信息，数量为：{}",message.size());

		try {
			LocationRunnable locationRunnable = new LocationRunnable(message);
			MyThreadUtils.getThreadPoolExecutor().execute(locationRunnable);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			return ConsumeResultStatus.SUCCESS;
		}
	}

	class LocationRunnable implements Runnable{

		// private MQEvent<T808Message> message;
		private List<MQEvent<T808Message>> messageList;

		LocationRunnable(List<MQEvent<T808Message>> messageList) {
			this.messageList = messageList;
		}

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			for(MQEvent<T808Message> message : messageList) {
				ResNettyModel<LocationModel> resNettyModel = new ResNettyModel();

				// 获取位置信息数据
				JT_0200 jT_0200 = (JT_0200)message.getContent().getMessageBody();
				LocationBasicInfo basicInfo = jT_0200.getBasicInfo();

				LocationModel locationModel = new LocationModel();
				locationModel.setSimNo(message.getContent().getSimNo());
				locationModel.setDirection(basicInfo.getDirection());
				locationModel.setSendTime(message.getContent().getTerminalSendTime());

				// 转换速度
				locationModel.setSpeed(String.valueOf(basicInfo.getSpeed()/10.0));

				// 查询车牌号和车辆ID
				VehicleBaseData vehicleDataBySimNo = myRedisServer.getVehicleDataBySimNo(message.getContent().getSimNo());
				locationModel.setPlateNo(vehicleDataBySimNo.getPlateNo());
				locationModel.setVehicleId(String.valueOf(vehicleDataBySimNo.getVehicleId()));

				// 转换经纬度
				// PointLatLng pointLatLng = changeLatLng(basicInfo.getLatitude(), basicInfo.getLongitude());
				locationModel.setLatitude(String.valueOf(basicInfo.getLatitude()/1000000.000000));
				locationModel.setLongitude(String.valueOf(basicInfo.getLongitude()/1000000.000000));

				// 封装数据结果集
				resNettyModel.setContent(locationModel);
				resNettyModel.setMessage(MSG_LOCAL);
				resNettyModel.setMsgCode(MyMessageCode.CODE_LOCAT);
				resNettyModel.setStatus(true);
				// 根据sim卡号获取所有用户 循环发送消息
				List userIds = myRedisServer.getUsersBySimNo(message.getContent().getSimNo());
				for(Object userId : userIds) {
					ChannelConfigUtils.sendSocketMessages(Long.parseLong(userId.toString()),resNettyModel);
				}
			}
			long endTime = System.currentTimeMillis();
			logger.info("批量消费位置信息完成，时间为：{}",startTime - endTime);
		}
	}

}
