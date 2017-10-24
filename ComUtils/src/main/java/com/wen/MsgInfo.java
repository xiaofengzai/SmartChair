package com.wen;

import java.io.Serializable;

/**
 * Created by wenfeng on 2017/10/24.
 */
public class MsgInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer magicNumber;
    private Integer msgType;
    private Integer msgBody;
    public MsgInfo(){}
    public MsgInfo(Integer msgType){
        this.msgType=msgType;
        this.magicNumber=Constant.MAGIC_NUMBER;
        this.msgBody=0x00;
    }

    public Integer getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(Integer magicNumber) {
        this.magicNumber = magicNumber;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(Integer msgBody) {
        this.msgBody = msgBody;
    }

    /*
    1.消息类型
    0x00:ping
    0x01:心跳
    0x02:支付

    2.消息结果
    0x00:成功
    0x01:失败

    金额





     */
}
