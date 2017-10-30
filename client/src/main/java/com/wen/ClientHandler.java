package com.wen;

import com.wen.proto.SmartChairProto;
import com.wen.proto.SmartChairProto.MsgInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by yaozb on 15-4-11.
 */
public class ClientHandler extends ChannelHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    MsgInfo msgInfo=MsgInfo.newBuilder().setMsgType(SmartChairProto.MSG_TYPE.HEART).build();
                    ctx.writeAndFlush(msgInfo);
                    System.out.println(DateUtils.getCurrentDate()+":send heart to server----------");
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 处理服务端的消息（ping命令，心跳回复，支付回复）
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        MsgInfo req=(MsgInfo)msg;
        SmartChairProto.MSG_TYPE msgType= req.getMsgType();
        MsgInfo.Builder respBuilder=MsgInfo.newBuilder();
        switch (msgType.getNumber()){
            //ping
            case 0x00:{
//                MessageInfo pingMsg=new MessageInfo(MsgTypeEnum.PING.getValue());
//                pingMsg.setMsgBody(ResultTypeEnum.SUCCESS.getValue());
//                channelHandlerContext.writeAndFlush(pingMsg);
                System.out.println(DateUtils.getCurrentDate()+":receive ping from server----------");
            }break;
            //heart
            case 0x02:{
                System.out.println(DateUtils.getCurrentDate()+":receive heart from server----------");
            }break;
            //pay
            case 0x03:{
                MsgInfo resp=respBuilder.setMsgType(SmartChairProto.MSG_TYPE.PAY).setInfo(20).build();
                System.out.println(DateUtils.getCurrentDate()+":收到金额"+20);
                channelHandlerContext.writeAndFlush(resp);
            }break;
            default:break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        MsgInfo msgInfo=MsgInfo.newBuilder().setMsgType(SmartChairProto.MSG_TYPE.LOGIN).build();
        ctx.writeAndFlush(msgInfo);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
