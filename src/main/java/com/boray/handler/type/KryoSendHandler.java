
package com.boray.handler.type;


import com.boray.handler.MessageSendHandler;
import com.boray.serialize.kryo.KryoCodecUtil;
import com.boray.serialize.kryo.KryoDecoder;
import com.boray.serialize.kryo.KryoEncoder;
import com.boray.serialize.kryo.KryoPoolFactory;
import io.netty.channel.ChannelPipeline;

public class KryoSendHandler implements NettyRpcSendHandler {

    public void handle(ChannelPipeline pipeline) {
        KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());
        pipeline.addLast(new KryoEncoder(util));
        pipeline.addLast(new KryoDecoder(util));
        pipeline.addLast(new MessageSendHandler());
    }
}

