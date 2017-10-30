package test;

/**
 * Created by wenfeng on 2017/10/30.
 */
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;



public class ProtoBufServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RichManProto.RichMan req = (RichManProto.RichMan) msg;
        System.out.println(req.getName()+"他有"+req.getCarsCount()+"量车");
        List<RichManProto.RichMan.Car> lists = req.getCarsList();
        if(null != lists) {

            for(RichManProto.RichMan.Car car : lists){
                System.out.println(car.getName());
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
