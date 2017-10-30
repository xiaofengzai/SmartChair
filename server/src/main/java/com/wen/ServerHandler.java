package com.wen;

import com.wen.EnumUtils.MsgTypeEnum;
import com.wen.proto.SmartChairProto;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by yaozb on 15-4-11.
 */
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove((SocketChannel)ctx.channel());
    }

    /**
     * 处理客户端回复消息（登录，心跳，支付回复）
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String hostInfo=HostUtil.getHostInfo(ctx.channel());
        SmartChairProto.MsgInfo req = (SmartChairProto.MsgInfo) msg;
        SmartChairProto.MSG_TYPE msgType= req.getMsgType();
        SmartChairProto.MsgInfo.Builder replayBuilder= SmartChairProto.MsgInfo.newBuilder();
        SmartChairProto.MsgInfo replay=null;
        if(MsgTypeEnum.LOGIN.getValue().equals(msgType)){

            if(true){
                //登录成功,把channel存到服务端的map中
                System.out.println(hostInfo);
                NettyChannelMap.add(hostInfo,(SocketChannel)ctx.channel());
                System.out.println(DateUtils.getCurrentDate()+":client"+hostInfo+" 登录成功");
            }
        }else{
            if(NettyChannelMap.get(hostInfo)==null){
                //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                replay=replayBuilder.setImei("").setInfo(1).setMsgType(SmartChairProto.MSG_TYPE.LOGIN).build();
                ctx.channel().writeAndFlush(replay);
            }
        }
        switch (msgType.getNumber()){
            case 0x02 :{
                System.out.println(DateUtils.getCurrentDate()+":收到客户端"+hostInfo+"的心跳包");
            }break;
            case 0x03:{
                //收到客户端收款回复
                replay=replayBuilder.setImei("").setInfo(0).setMsgType(SmartChairProto.MSG_TYPE.PAY).build();
                NettyChannelMap.get(hostInfo).writeAndFlush(replay);
                System.out.println(DateUtils.getCurrentDate()+":收到客户端"+hostInfo+"的收款回复");

            }break;
            default:break;
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
