package com.aisafer.webgis.model;

/**
 * socket参数接收实体类
 *
 * @Author:weiyuanlong
 * @Date: Created in
 * @Modified By:
 */
public class SocketRequestMessage {

    /** 实现类名 */
    private String msgId;

    /** sim卡号 */
    private String simNo;

    /** 用户ID */
    private Long userId;

    /** 消息体 */
    private String content;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSimNo() {
        return simNo;
    }

    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
