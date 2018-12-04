package com.aisafer.webgis.netty.message;

import com.aisafer.psg.user.client.dto.UserReq;
import com.aisafer.psg.user.client.dto.UserRes;
import com.aisafer.psg.user.client.service.RUserService;
import com.aisafer.webgis.model.AlarmModel;
import com.aisafer.webgis.model.MyMessageCode;
import com.aisafer.webgis.model.ResNettyModel;
import com.aisafer.webgis.utils.ChannelConfigUtils;
import com.polaris.base.utils.string.JsonUtil;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * cocket鉴权处理
 *
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
@Component("authcReceiveHandle")
public class AuthcReceiveHandle implements ReceiveHandle {

    /** 日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AuthcReceiveHandle.class);

    /** 操作用户的实体类 */
    @Resource
    private RUserService rUserService;

    /** 登录成功 */
    @Value("${msg.login.suc}")
    private String MSG_LOGIN_SUC;

    /** 登录失败 */
    @Value("${msg.login.def}")
    private String MSG_LOGIN_DEF;

    /**
     * 用户鉴权
     *
     * @param userId
     * @param simMo
     */
    @Override
    public void handleMessage(Long userId, String simMo, ChannelHandlerContext ctx) {
        LOGGER.info("接受参数 userId ： {}  simNo ： {}",userId,simMo);
        ResNettyModel<AlarmModel> resNettyModel = new ResNettyModel();
        resNettyModel.setMsgCode(MyMessageCode.LOGIN_AUTH);

        UserReq userReq = new UserReq();
        userReq.setId(userId);

        try {
            UserRes userById = rUserService.getUserById(userReq);
            if(userById == null) {
                // 未查询到用户 鉴权失败
                resNettyModel.setStatus(false);
                resNettyModel.setMessage(MSG_LOGIN_DEF);
            }else {
                // 保存用户连接 鉴权成功
                ChannelConfigUtils.putUserSocket(userId,ctx);
                resNettyModel.setStatus(true);
                resNettyModel.setMessage(MSG_LOGIN_SUC);
            }
        }catch (Exception e) {
            // 出异常 鉴权失败
            e.printStackTrace();
            LOGGER.error(e.getMessage());
            resNettyModel.setStatus(false);
            resNettyModel.setMessage(MSG_LOGIN_DEF);
        }finally {
            String resultJson = JsonUtil.bean2Json(resNettyModel);
            ChannelConfigUtils.sendSocketMessage(ctx,resultJson);
        }
    }

}
