package com.wen;

import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

/**
 * Created by wenfeng on 2017/10/24.
 */
public class HostUtil {
    public static SocketAddress getClientHostInfo(Channel channel){
        return  channel.remoteAddress();
    }
    public static String getHostInfo(Channel channel){
        return getClientHostInfo(channel).toString();
    }
}
