package com.aisafer.webgis.utils;

import com.aisafer.webgis.model.ResNettyModel;
import com.polaris.base.utils.string.JsonUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * socket数据工具类
 *
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
public class ChannelConfigUtils {

    /** 日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelConfigUtils.class);

    /** userId:{socket标识:socket} */
    private static ConcurrentHashMap<Long,ConcurrentHashMap<String,ChannelHandlerContext>> userChannel;

    /**
     * 发送soocket消息
     *
     * @param ctx
     * @param msg
     */
    public static void sendSocketMessage(ChannelHandlerContext ctx, String msg) {
        String socketId = getSocketId(ctx);
        // LOGGER.info("给用户ID为：{}，发送消息",socketId);
        TextWebSocketFrame tws = new TextWebSocketFrame(msg);
        ctx.channel().writeAndFlush(tws);
    }

    /**
     * 根据用户ID对所有socket发送消息
     *
     * @param userId
     * @param nettyModel
     */
    public static void sendSocketMessages(Long userId, ResNettyModel nettyModel) {
        // 获取用户所有连接
        Collection<ChannelHandlerContext> userSockets = getUserSockets(userId);
        String resultJson = JsonUtil.bean2Json(nettyModel);

        // 发送消息
        for(ChannelHandlerContext channelHandlerContext : userSockets) {
            sendSocketMessage(channelHandlerContext,resultJson);
        }
    }

    /**
     * 根据连接获取唯一通道标识
     *
     * @param ctx
     * @return
     */
    private static String getSocketId(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        // String[] split = channel.toString().split(" ");
        ChannelId id = channel.id();

        return id.asLongText();
    }

    /**
     * 添加用户连接的socket
     *
     * @param userId
     * @param ctx
     * @return
     */
    public static Boolean putUserSocket(Long userId, ChannelHandlerContext ctx) {
        String socketId = getSocketId(ctx);
        LOGGER.info("用户新增连接 用户ID为 ： {}， 通道号为： {}  唯一标识为 ： {}",userId,ctx.channel().id(),socketId);
        try {
            if(userChannel == null || userChannel.size() < 1) {
                // 连接集合为空
                userChannel = new ConcurrentHashMap<>();
                ConcurrentHashMap<String,ChannelHandlerContext> userSocket = new ConcurrentHashMap<>();
                userSocket.put(socketId,ctx);
                userChannel.put(userId,userSocket);
            }else {
                ConcurrentHashMap<String, ChannelHandlerContext> userId1 = userChannel.get(userId);
                if(userId1 == null || userId1.size() < 1) {
                    // 此用户没有任何一个连接
                    ConcurrentHashMap<String,ChannelHandlerContext> userSocket = new ConcurrentHashMap<>();
                    userSocket.put(socketId,ctx);
                    userChannel.put(userId,userSocket);
                }else {
                    // 此用户有socket连接 在map中添加一个socket
                    userId1.put(socketId,ctx);
                    userChannel.put(userId,userId1);
                }
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("用户新增连接失败  ：" + e.getMessage());
            return false;
        }
    }

    /**
     * 根据用户ID获取所有了socket连接
     *
     * @param userId
     * @return
     */
    private static Collection<ChannelHandlerContext> getUserSockets(Long userId) {
        if(userChannel == null)
            return new ArrayList<>();

        ConcurrentHashMap<String, ChannelHandlerContext> userSockets = userChannel.get(userId);
        if(userSockets != null) {
            Collection<ChannelHandlerContext> sockets = userSockets.values();
            return sockets;
        }
        return new ArrayList<>();
    }

    /**
     * 移除此用户连接
     *
     * @param ctx
     * @return true 移除成功   false移除失败
     */
    public static Boolean removeUserSocket(ChannelHandlerContext ctx) {
        String channelId = getSocketId(ctx);

        Collection<ConcurrentHashMap<String, ChannelHandlerContext>> userSocket = userChannel.values();
        for(Map<String, ChannelHandlerContext> sockets : userSocket) {
            if(sockets.containsKey(channelId)) {
                sockets.remove(channelId);
                LOGGER.info("移除唯一标识为 ： {} ，的通道",channelId);
                return true;
            }
        }
        return false;
    }

}
