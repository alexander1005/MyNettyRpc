
package com.boray.handler.type;

import com.boray.handler.MessageRecvHandler;
import com.boray.handler.NettyRpcRecvHandler;
import com.boray.serialize.protostuff.ProtostuffCodecUtil;
import com.boray.serialize.protostuff.ProtostuffDecoder;
import com.boray.serialize.protostuff.ProtostuffEncoder;
import io.netty.channel.ChannelPipeline;

import java.util.Map;


public class ProtostuffRecvHandler implements NettyRpcRecvHandler {

    public void handle(Map<String, Object> handlerMap, ChannelPipeline pipeline) {
        ProtostuffCodecUtil util = new ProtostuffCodecUtil();
        util.setRpcDirect(true);
        pipeline.addLast(new ProtostuffEncoder(util));
        pipeline.addLast(new ProtostuffDecoder(util));
        pipeline.addLast(new MessageRecvHandler(handlerMap));
    }
}

