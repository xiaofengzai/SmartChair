package com.wen;

import com.wen.proto.SmartChairProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;


/**
 * Created by wenfeng on 17-10-24.
 */
public class NettyServerBootstrap {
    private int port;
    private SocketChannel socketChannel;
    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup worker=new NioEventLoopGroup();
        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(boss,worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                p.addFirst("decoder", new ProtobufVarint32FrameDecoder());
                p.addLast(new ProtobufDecoder(SmartChairProto.MsgInfo.getDefaultInstance()));
//                p.addLast(new ObjectEncoder());
//                p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                p.addLast(new ProtobufEncoder());
                p.addLast(new ServerHandler());
            }
        });
        ChannelFuture f= bootstrap.bind(port).sync();
        worker.execute(new TaskThread());
        if(f.isSuccess()){
            System.out.println("server start---------------");
        }
    }
    public static void main(String []args) throws InterruptedException {
       new NettyServerBootstrap(9999);
    }
}
