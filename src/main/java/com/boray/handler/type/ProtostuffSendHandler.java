
package com.boray.handler.type;


import com.boray.handler.MessageSendHandler;
import com.boray.serialize.protostuff.ProtostuffCodecUtil;
import com.boray.serialize.protostuff.ProtostuffDecoder;
import com.boray.serialize.protostuff.ProtostuffEncoder;
import io.netty.channel.ChannelPipeline;


public class ProtostuffSendHandler implements NettyRpcSendHandler {

    public void handle(ChannelPipeline pipeline) {
        ProtostuffCodecUtil util = new ProtostuffCodecUtil();
        util.setRpcDirect(false);
        pipeline.addLast(new ProtostuffEncoder(util));
        pipeline.addLast(new ProtostuffDecoder(util));
        pipeline.addLast(new MessageSendHandler());
    }
}
