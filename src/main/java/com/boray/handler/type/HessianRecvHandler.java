
package com.boray.handler.type;

import com.boray.handler.MessageRecvHandler;
import com.boray.handler.NettyRpcRecvHandler;
import com.boray.serialize.hessian.HessianCodecUtil;
import com.boray.serialize.hessian.HessianDecoder;
import com.boray.serialize.hessian.HessianEncoder;
import io.netty.channel.ChannelPipeline;

import java.util.Map;


public class HessianRecvHandler implements NettyRpcRecvHandler {
    public void handle(Map<String, Object> handlerMap, ChannelPipeline pipeline) {
        HessianCodecUtil util = new HessianCodecUtil();
        pipeline.addLast(new HessianEncoder(util));
        pipeline.addLast(new HessianDecoder(util));
        pipeline.addLast(new MessageRecvHandler(handlerMap));
    }
}

