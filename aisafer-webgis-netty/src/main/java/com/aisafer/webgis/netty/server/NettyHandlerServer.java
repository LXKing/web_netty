package com.aisafer.webgis.netty.server;

import com.aisafer.webgis.model.MNettyHandler;
import com.aisafer.webgis.netty.message.ReceiveHandle;
import com.aisafer.webgis.model.SocketRequestMessage;
import com.aisafer.webgis.utils.SpringContextUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
@Component("nettyHandlerServer")
public class NettyHandlerServer {

    /** 日志 */
    protected static final Logger LOGGER = LoggerFactory.getLogger(NettyHandlerServer.class);

    private WebSocketServerHandshaker handshaker;

    public void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        // 如果HTTP解码失败，返回HTTP异常
        if (!req.getDecoderResult().isSuccess() || (!MNettyHandler.WEBSOCKET.equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        //获取url后置参数
        HttpMethod method = req.getMethod();
        String uri = req.getUri();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        // Map<String, List<String>> parameters = queryStringDecoder.parameters();

        if(method == HttpMethod.GET && MNettyHandler.URI_WEBSSS.equals(uri)){
            // anzhuo
            ctx.attr(AttributeKey.valueOf(MNettyHandler.TYPE)).set("anzhuo");
        }else if(method == HttpMethod.GET && MNettyHandler.URI_WEBSOCKET.equals(uri)){
            // live
            ctx.attr(AttributeKey.valueOf(MNettyHandler.TYPE)).set("live");
        }
        // 构造握手响应返回，本机测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://"+req.headers().get(HttpHeaders.Names.HOST)+uri, null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * socket消息处理
     *
     * @param ctx
     * @param frame
     */
    public void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        // 处理请求消息内容
        String request = ((TextWebSocketFrame) frame).text();

        LOGGER.info(String.format("%s received %s  name %s", ctx.channel().id(), request,ctx.name()));
        SocketRequestMessage socketRequestMessage = JSONObject.parseObject(request, SocketRequestMessage.class);
        // 实例化请求bean实现类进行业务处理
        ReceiveHandle instanceBean = SpringContextUtil.getInstanceBean(socketRequestMessage.getMsgId(), ReceiveHandle.class);
        instanceBean.handleMessage(socketRequestMessage.getUserId(),socketRequestMessage.getSimNo(),ctx);
    }

}
