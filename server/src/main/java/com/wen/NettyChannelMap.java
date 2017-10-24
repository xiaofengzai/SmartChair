package com.wen;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaozb on 15-4-11.
 */
public class NettyChannelMap {
    private static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();
    public static void add(String clientInfo,SocketChannel socketChannel){
        map.put(clientInfo,socketChannel);
    }
    public static Channel get(String clientInfo){
       return map.get(clientInfo);
    }
    public static void remove(SocketChannel socketChannel){
        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                map.remove(entry.getKey());
            }
        }
    }
    public static boolean isEmpty(){
        return map.isEmpty();
    }

    public static Map<String, SocketChannel> getMap() {
        return map;
    }

}
