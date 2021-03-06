package com.wen;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by wenfeng on 2017/10/24.
 */
public class MsgEncoder extends MessageToByteEncoder<MessageInfo> {
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageInfo messageInfo, ByteBuf byteBuf) throws Exception {
        byte[] body = toByteArray(messageInfo);  //将对象转换为byte，伪代码，具体用什么进行序列化，你们自行选择。可以使用我上面说的一些
        if(body.length!=Constant.PACKET_LENGTH){
            channelHandlerContext.close();
        }else{
            byteBuf.writeInt(Constant.PACKET_LENGTH);  //先将消息长度写入，也就是消息头
            byteBuf.writeBytes(body);
        }
    }

    public byte[] toByteArray (MessageInfo messageInfo) {
        byte[] bytes = new byte[Constant.PACKET_LENGTH];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        if(messageInfo.getMagicNumber()!=Constant.MAGIC_NUMBER){
            return null;
        }
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeInt(Constant.MAGIC_NUMBER);
            oos.writeInt(messageInfo.getMsgType());
            oos.writeInt(messageInfo.getMsgBody());
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

        return bytes;
    }
}
