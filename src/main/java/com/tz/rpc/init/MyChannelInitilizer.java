package com.tz.rpc.init;

import com.tz.rpc.handler.RegistryHandlers;
import com.tz.rpc.registry.Registry;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class MyChannelInitilizer extends ChannelInitializer<SocketChannel> {



    protected void initChannel(SocketChannel channel) throws Exception {

        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        pipeline.addLast(new LengthFieldPrepender(4));
        pipeline.addLast("encode",new ObjectEncoder());
        pipeline.addLast("decode",new ObjectDecoder(ClassResolvers.cacheDisabled(null)));

        //业务

        pipeline.addLast(new RegistryHandlers());
    }
}
