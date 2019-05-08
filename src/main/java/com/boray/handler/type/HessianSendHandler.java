
package com.boray.handler.type;


import com.boray.handler.MessageSendHandler;
import com.boray.serialize.hessian.HessianCodecUtil;
import com.boray.serialize.hessian.HessianDecoder;
import com.boray.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelPipeline;


public class HessianSendHandler implements NettyRpcSendHandler {
    public void handle(ChannelPipeline pipeline) {
        HessianCodecUtil util = new HessianCodecUtil();
        pipeline.addLast(new HessianEncoder(util));
        pipeline.addLast(new HessianDecoder(util));
        pipeline.addLast(new MessageSendHandler());
    }
}

