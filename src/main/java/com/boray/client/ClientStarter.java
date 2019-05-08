package com.boray.client;

import com.boray.handler.MessageRecvHandler;
import com.boray.handler.MessageSendHandler;
import com.boray.model.MessageRequest;
import com.boray.model.Test;
import com.boray.serialize.MessageCodecUtil;
import com.boray.serialize.kryo.KryoCodecUtil;
import com.boray.serialize.kryo.KryoDecoder;
import com.boray.serialize.kryo.KryoEncoder;
import com.boray.serialize.kryo.KryoPoolFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.List;
import java.util.UUID;

public class ClientStarter {

    static {




    }

    public static void main(String[] args) throws InterruptedException {

        new ClientStarter().start(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
    }


    public  void start(String host, int port,int kb,int s) throws InterruptedException {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());
                            pipeline.addLast(new KryoEncoder(util));
                            pipeline.addLast(new KryoDecoder(util));
                            pipeline.addLast(new MessageRecvHandler(null));
                        }
                    });

            ChannelFuture future = b.connect(host, port).sync();

//            MessageRequest request  = new MessageRequest();
//            request.setMessageId(UUID.randomUUID().toString());
//            request.setMethodName("no");
//            request.setTopic("dasdasd");
//            request.setTypeParameters(new Class[]{String.class,Integer.class});
//            request.setParametersVal(new Object[]{"1",123});
            Channel channel = future.channel();

            MessageRequest request = new MessageRequest();
            channel.writeAndFlush(request);

            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }


    }

}
