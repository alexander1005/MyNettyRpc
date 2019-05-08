package com.boray.handler;

import com.boray.protocal.RpcRecvSerializeFrame;
import com.boray.protocal.RpcSerializeFrame;
import com.boray.protocal.RpcSerializeProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


public class MyChannelInitializer  extends ChannelInitializer<SocketChannel> {


    private RpcSerializeProtocol protocol;
    private RpcSerializeFrame frame = new RpcRecvSerializeFrame();

    public MyChannelInitializer buildRpcSerializeProtocol(RpcSerializeProtocol protocol) {
        this.protocol = protocol;
        return this;
    }



    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        System.out.println("开始注册");
        ChannelPipeline pipeline = socketChannel.pipeline();

        frame.select(protocol,pipeline);
        System.out.println("注册成功");
    }

}
