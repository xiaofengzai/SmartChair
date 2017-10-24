package com.wen;

import com.wen.Constant;
import com.wen.MsgInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.util.List;

/**
 * Created by wenfeng on 2017/10/24.
 */
public class MyFixedLengthDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public MyFixedLengthDecoder(int frameLength) {
        if(frameLength <= 0) {
            throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
        } else {
            this.frameLength = frameLength;
        }
    }

    protected final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        MsgInfo decoded = this.decode(ctx, in);
        if(decoded != null) {
            out.add(decoded);
        }

    }

    protected MsgInfo decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Integer length=in.readableBytes();
        if(length==0)
            return null;
        Integer magicNumber=in.readInt();
        if(length!=Constant.PACKET_LENGTH ||magicNumber!= Constant.MAGIC_NUMBER)
            return null;
        MsgInfo msgInfo=new MsgInfo();
        msgInfo.setMagicNumber(magicNumber);
        msgInfo.setMsgType(in.readInt());
        msgInfo.setMsgBody(in.readInt());
        return msgInfo;
    }
}
