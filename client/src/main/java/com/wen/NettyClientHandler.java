//package com.wen;
//
//import com.wen.EnumUtils.MsgTypeEnum;
//import com.wen.EnumUtils.ResultTypeEnum;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.util.ReferenceCountUtil;
//
///**
// * Created by yaozb on 15-4-11.
// */
//public class NettyClientHandler extends SimpleChannelInboundHandler<MessageInfo> {
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if (evt instanceof IdleStateEvent) {
//            IdleStateEvent e = (IdleStateEvent) evt;
//            switch (e.state()) {
//                case WRITER_IDLE:
//                    MessageInfo heartMsg=new MessageInfo(MsgTypeEnum.HEART.getValue());
//                    ctx.writeAndFlush(heartMsg);
//                    System.out.println(DateUtils.getCurrentDate()+":send heart to server----------");
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    /**
//     * 处理服务端的消息（ping命令，心跳回复，支付回复）
//     * @param channelHandlerContext
//     * @param msgInfo
//     * @throws Exception
//     */
//    @Override
//    protected void messageReceived(ChannelHandlerContext channelHandlerContext, MessageInfo msgInfo) throws Exception {
//        Integer msgType=msgInfo.getMsgType();
//        switch (msgType){
//            //ping
//            case 0x00:{
//                MessageInfo pingMsg=new MessageInfo(MsgTypeEnum.PING.getValue());
//                pingMsg.setMsgBody(ResultTypeEnum.SUCCESS.getValue());
//                channelHandlerContext.writeAndFlush(pingMsg);
//                System.out.println(DateUtils.getCurrentDate()+":receive ping from server----------");
//            }break;
//            //heart
//            case 0x02:{
//                System.out.println(DateUtils.getCurrentDate()+":receive heart from server----------");
//            }break;
//            //pay
//            case 0x03:{
//                MessageInfo payMsg=new MessageInfo(MsgTypeEnum.PAY.getValue());
//                payMsg.setMsgBody(ResultTypeEnum.SUCCESS.getValue());
//                System.out.println(DateUtils.getCurrentDate()+":收到金额"+payMsg.getMsgBody());
//                channelHandlerContext.writeAndFlush(payMsg);
//            }break;
//            default:break;
//        }
//        ReferenceCountUtil.release(msgInfo);
//    }
//}
