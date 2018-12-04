package com.aisafer.webgis.netty.server;

import com.aisafer.webgis.utils.ChannelConfigUtils;
import com.aisafer.webgis.utils.SpringContextUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty逻辑实现类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-24 10:54:18
 * @Modified By:
 */
public class MyWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    /** 日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(MyWebSocketServerHandler.class);

    /** netty业务处理实现类 */
    private NettyHandlerServer nettyHandlerServer = (NettyHandlerServer) SpringContextUtil.getInstanceBean("nettyHandlerServer");

    /**
     * 接收客户端发送的消息 channel 通道 Read 读 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据。但是这个数据在不进行解码时它是ByteBuf类型的
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        LOGGER.info("messageReceived 参数为: {}",msg);
        if (msg instanceof FullHttpRequest) {
            LOGGER.info("http");
            nettyHandlerServer.handleHttpRequest(channelHandlerContext, ((FullHttpRequest) msg));
        }else if (msg instanceof WebSocketFrame) {
            LOGGER.info("WebSocket");
            nettyHandlerServer.handlerWebSocketFrame(channelHandlerContext, (WebSocketFrame) msg);
        }
    }

    /**
     * channel 通道 action 活跃的 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("客户端与服务端连接开启：" + ctx.channel().remoteAddress().toString());
    }

    /**
     * channel 通道 Inactive 不活跃的 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelConfigUtils.removeUserSocket(ctx);
        LOGGER.info("客户端与服务端连接关闭：" + ctx.channel().remoteAddress().toString());
    }

    /**
     * channel 通道 Read 读取 Complete 完成 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("channelReadComplete");
        ctx.flush();
    }

    /**
     * exception 异常 Caught 抓住 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("出现异常  异常原因： {}", cause.getMessage());
        ChannelConfigUtils.removeUserSocket(ctx);
        cause.printStackTrace();
    }

}

