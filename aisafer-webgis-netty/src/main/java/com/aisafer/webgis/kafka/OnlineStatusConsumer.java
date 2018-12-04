package com.aisafer.webgis.kafka;

import com.aisafer.dpc.client.dto.custom.OnlineStatus;
import com.aisafer.webgis.model.MyMessageCode;
import com.aisafer.webgis.model.OnlineModel;
import com.aisafer.webgis.model.ResNettyModel;
import com.aisafer.webgis.model.VehicleBaseData;
import com.aisafer.webgis.netty.server.MyRedisServer;
import com.aisafer.webgis.netty.server.UpdatePermissionServer;
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
 * 处理车辆上下线
 *
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
@Service
public class OnlineStatusConsumer extends BaseUtils {

	/** 日志 */
	private static Logger logger = LoggerFactory.getLogger(OnlineStatusConsumer.class);

	/** 更新车辆权限的对象 */
	@Resource
	private UpdatePermissionServer updatePermissionServer;

	/** 操作redis服务对象 */
	@Resource
	private MyRedisServer myRedisServer;

	/** 车辆上线 */
	@Value("${msg.online}")
	private String CODE_MSG_LINE_ON;

	/** 车辆下线 */
	@Value("${msg.outline}")
	private String CODE_MSG_LINE_OUT;

	/**
	 * 监听车辆上下线
	 *
	 * @param contentList
	 * @return
	 */
	@KafkaListener(group = "$groups[webgis]", topic = "$topics[onlinestatus]")
	public ConsumeResultStatus msg(List<MQEvent<OnlineStatus>> contentList) {
		try {
			OnlineStatusRunnable onlineStatusRunnable = new OnlineStatusRunnable(contentList);
			MyThreadUtils.getThreadPoolExecutor().execute(onlineStatusRunnable);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("OnlineStatusConsumer出错  错误原因： {}",  e.getMessage());
		}finally {
			return ConsumeResultStatus.SUCCESS;
		}
	}

	class OnlineStatusRunnable implements Runnable{

		private List<MQEvent<OnlineStatus>> contentList;

		OnlineStatusRunnable(List<MQEvent<OnlineStatus>> contentList){
			this.contentList = contentList;
		}

		@Override
		public void run() {
			for(MQEvent<OnlineStatus> content : contentList) {
				OnlineStatus onlineStatus = content.getContent();
				logger.debug("OnlineStatusConsumer.msg 参数为 ： {}",JsonUtil.bean2Json(onlineStatus));
				// 车辆上线
				OnlineModel onlineModel = new OnlineModel();
				VehicleBaseData vehicleDataBySimNo = myRedisServer.getVehicleDataBySimNo(onlineStatus.getSimNo());
				onlineModel.setPlateNo(vehicleDataBySimNo.getPlateNo());
				onlineModel.setVehicleId(String.valueOf(vehicleDataBySimNo.getVehicleId()));

				// 封装发送消息
				onlineModel.setSimNo(onlineStatus.getSimNo());
				onlineModel.setStatus(onlineStatus.getState());
				onlineModel.setSendTime(onlineStatus.getStateTime());

				ResNettyModel<OnlineModel> resNettyModel = new ResNettyModel();
				resNettyModel.setContent(onlineModel);
				if(onlineModel.getStatus() == 1){
					resNettyModel.setMsgCode(MyMessageCode.CODE_LINE_ON);
					resNettyModel.setMessage(CODE_MSG_LINE_ON);
				}else {
					resNettyModel.setMsgCode(MyMessageCode.CODE_LINE_OUT);
					resNettyModel.setMessage(CODE_MSG_LINE_OUT);
				}
				resNettyModel.setStatus(true);
				// 根据sim卡号获取所有用户 循环发送消息
				List<Object> userIds = myRedisServer.getUsersBySimNo(onlineStatus.getSimNo());
				for(Object userId : userIds) {
					ChannelConfigUtils.sendSocketMessages(Long.parseLong(userId.toString()),resNettyModel);
				}
			}
		}
	}

}
