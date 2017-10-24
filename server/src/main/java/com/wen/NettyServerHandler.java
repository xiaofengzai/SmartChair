package com.wen;

import com.wen.EnumUtils.MsgTypeEnum;
import com.wen.EnumUtils.ResultTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

import static com.wen.Constant.HEART;
import static com.wen.Constant.PAY;
import static com.wen.Constant.PING;

/**
 * Created by yaozb on 15-4-11.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<MsgInfo> {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove((SocketChannel)ctx.channel());
    }

    /**
     * 处理客户端回复消息（登录，心跳，支付回复）
     * @param channelHandlerContext
     * @param msgInfo
     * @throws Exception
     */
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, MsgInfo msgInfo) throws Exception {
        String hostInfo=HostUtil.getHostInfo(channelHandlerContext.channel());
        Integer msgType=msgInfo.getMsgType();
        if(MsgTypeEnum.LOGIN.getValue().equals(msgType)){

            if(true){
                //登录成功,把channel存到服务端的map中
                System.out.println(hostInfo);
                NettyChannelMap.add(hostInfo,(SocketChannel)channelHandlerContext.channel());
                System.out.println(DateUtils.getCurrentDate()+":client"+hostInfo+" 登录成功");
            }
        }else{
            if(NettyChannelMap.get(hostInfo)==null){
                    //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                    channelHandlerContext.channel().writeAndFlush(hostInfo);
            }
        }
        switch (msgType){
            case 0x02 :{
                MsgInfo pingMsg=new MsgInfo(HEART);
                pingMsg.setMsgBody(ResultTypeEnum.SUCCESS.getValue());
                System.out.println(DateUtils.getCurrentDate()+":收到客户端"+hostInfo+"的心跳包");
                NettyChannelMap.get(hostInfo).writeAndFlush(pingMsg);
            }break;
            case 0x03:{
                //收到客户端收款回复
                MsgInfo payMsg=new MsgInfo(PAY);
                payMsg.setMsgBody(10);
                NettyChannelMap.get(hostInfo).writeAndFlush(payMsg);
                System.out.println(DateUtils.getCurrentDate()+":收到客户端"+hostInfo+"的收款回复");

            }break;
            default:break;
        }
        ReferenceCountUtil.release(msgInfo);
    }
}
