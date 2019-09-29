package edu.whut.ruansong.demo_display3.utils;

import android.os.Handler;
import android.util.Log;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

/**
 * Created by Android Studio.
 * User: lvdou-jack
 * Date: 2019/9/13
 * Time: 19:48
 */
public class ImageSocketClient extends Thread{
    private int port = 1234;
    private String server_ip = "47.103.21.160";
    private Handler mHandler = null;

    public ImageSocketClient(Handler h1){
        this.mHandler = h1;
    }

    @Override
    public void run(){
        Log.e("ImageSocketClient","client--进入构造函数");
        try{
            connect(port,server_ip);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void connect(int port,String server_ip)throws Exception{
        Log.e("ImageSocketClient","client--进入connect");
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
//                        int frameLength = 2;
                        @Override
                        public void initChannel(SocketChannel arg0) throws Exception {
                            //添加POJO对象解码器,禁止缓存类加载器
                            arg0.pipeline().addLast(new ObjectDecoder(
                                    ClassResolvers.cacheDisabled(
                                            this.getClass().getClassLoader())));
                            arg0.pipeline().addLast(new ImageClientHandler(mHandler));
                        }
                    });
            Log.e("ImageSocketClient","client--发起异步连接操作");
            ChannelFuture f = b.connect(server_ip,port).sync();
            Log.e("ImageSocketClient","client--等待客户端链路关闭");
            f.channel().closeFuture().sync();

        } finally {
            Log.e("ImageSocketClient","client--释放资源");
            group.shutdownGracefully();
        }
    }
}
