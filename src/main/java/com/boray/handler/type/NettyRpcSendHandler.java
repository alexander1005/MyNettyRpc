
package com.boray.handler.type;

import io.netty.channel.ChannelPipeline;


public interface NettyRpcSendHandler {
    void handle(ChannelPipeline pipeline);
}

