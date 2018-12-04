package com.aisafer.webgis.netty.message;

import io.netty.channel.ChannelHandlerContext;

/**
 * socket接收消息体接口
 *
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
public interface ReceiveHandle {

    void handleMessage(Long userId, String simMo ,ChannelHandlerContext ctx);

}
