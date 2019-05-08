package com.boray.protocal;

import io.netty.channel.ChannelPipeline;

public interface RpcSerializeFrame {

    void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);
}
