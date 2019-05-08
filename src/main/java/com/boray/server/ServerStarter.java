package com.boray.server;

import com.boray.config.RpcSystemConfig;
import com.boray.factory.NamedThreadFactory;
import com.boray.handler.MyChannelInitializer;
import com.boray.protocal.RpcSerializeProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadFactory;

public class ServerStarter {

    private static final int PARALLEL = RpcSystemConfig.SYSTEM_PROPERTY_PARALLEL * 2;
    private RpcSerializeProtocol serializeProtocol = RpcSerializeProtocol.KRYOSERIALIZE;
    ThreadFactory threadRpcFactory = new NamedThreadFactory("NettyRPC ThreadFactory");

    //boss
    EventLoopGroup boss = new NioEventLoopGroup();
    //worker
    EventLoopGroup worker = new NioEventLoopGroup(PARALLEL, threadRpcFactory, SelectorProvider.provider());



    public static void main(String[] args) throws InterruptedException {



        new ServerStarter().start(Integer.parseInt(args[0]));
    }



    private  void start(int port) throws InterruptedException {

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            ChannelFuture channelFuture = bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChannelInitializer().buildRpcSerializeProtocol(serializeProtocol))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .bind(port).sync();

            //异步阻塞
            channelFuture.channel().closeFuture().sync();

        } finally {

            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }

    }
}
