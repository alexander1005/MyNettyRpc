
package com.boray.handler;

import com.boray.model.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;


public class MessageRecvHandler extends ChannelInboundHandlerAdapter {

    private final Map<String, Object> handlerMap;

    public MessageRecvHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

   // private TopicMessageProcessor processor = new TopicMessageProcessor();

    private long count =0;
    private long size =0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        Test request = (Test) msg;
//
//        count++;
//        size += (request.getBytes().length);
//
        //MessageResponse response  = processor.process(msg);

        //ctx.channel().writeAndFlush(response);

       // System.out.println("服务端收到了消息"+count+"并回复了  收到总计大小 "+ size /1024/1024 + " MB" );
    }





    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

