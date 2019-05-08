
package com.boray.handler.type;

import com.boray.handler.MessageRecvHandler;
import com.boray.handler.NettyRpcRecvHandler;
import com.boray.serialize.kryo.KryoCodecUtil;
import com.boray.serialize.kryo.KryoDecoder;
import com.boray.serialize.kryo.KryoEncoder;
import com.boray.serialize.kryo.KryoPoolFactory;
import io.netty.channel.ChannelPipeline;

import java.util.Map;


public class KryoRecvHandler implements NettyRpcRecvHandler {
    public void handle(Map<String, Object> handlerMap, ChannelPipeline pipeline) {
        KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());
        pipeline.addLast(new KryoEncoder(util));
        pipeline.addLast(new KryoDecoder(util));
        pipeline.addLast(new MessageRecvHandler(handlerMap));
    }
}

