package edu.whut.ruansong.demo_display3.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Arrays;

import edu.whut.ruansong.demo_display3.bean.ImageMesBean;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/13
 * Time: 19:50
 */
public class ImageClientHandler extends ChannelInboundHandlerAdapter {
    //处理入站数据
    private Handler mHandler = null;

    public ImageClientHandler(Handler h1) {
//        Log.e("ImageClientHandler","client--进入构造函数");
        this.mHandler = h1;
    }

    //当客户端和服务端的TCP链路建立成功之后,NIO线程会调用此方法
    @Override
    public void channelActive(ChannelHandlerContext ctx){
        Log.e("ImageClientHandler","client--channelActive请求服务器");
        byte[] req = new byte[1543];
        for(int i = 0; i< 1543;i++){//用全0初始化数组
            req[i] = (byte)0;
        }
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);
    }

    //服务端返回消息时调用此方法
    @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg)throws Exception{
        Log.e("ImageClientHandler","client--channelRead服务端返回消息");
        /**
         * 测试语句
         * 请求通信,服务器返回string*/
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"UTF-8");
//        Log.e("NClientHandler","服务器返回消息 :"+body);
        /**
         * 测试语句
         * 服务器返回JavaBean对象*/
        ImageMesBean response = (ImageMesBean) msg;

        //与主线程通信
        Message image_data = new Message();
        image_data.what = 0;
        image_data.obj = response;
        mHandler.sendMessage(image_data);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)throws Exception{
        Log.e("ImageClientHandler","client--调用channelReadComplete");
//        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
