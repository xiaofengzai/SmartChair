package com.wen;

import io.netty.channel.socket.SocketChannel;

import java.util.Set;

/**
 * Created by wenfeng on 2017/10/24.
 */
public class TaskThread implements Runnable {
    public void run() {
        while(!NettyChannelMap.isEmpty()){
            Set<String> set=NettyChannelMap.getMap().keySet();
            for(String key:set){
                SocketChannel socketChannel=(SocketChannel)NettyChannelMap.get(key);
                MsgInfo payMsg=new MsgInfo(Constant.PAY);
                payMsg.setMsgBody(10);
                socketChannel.writeAndFlush(payMsg);
                System.out.println(key);
            }
        }
    }
}
