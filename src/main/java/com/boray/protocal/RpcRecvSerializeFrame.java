package com.boray.protocal;

import com.boray.handler.NettyRpcRecvHandler;
import com.boray.handler.type.HessianRecvHandler;
import com.boray.handler.type.JdkNativeRecvHandler;
import com.boray.handler.type.KryoRecvHandler;
import com.boray.handler.type.ProtostuffRecvHandler;
import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import io.netty.channel.ChannelPipeline;

import java.util.Map;

public class RpcRecvSerializeFrame  implements RpcSerializeFrame {


    private Map<String, Object> handlerMap = null;

    private static ClassToInstanceMap<NettyRpcRecvHandler> handler = MutableClassToInstanceMap.create();

    static {
            handler.putInstance(JdkNativeRecvHandler.class,new JdkNativeRecvHandler());
            handler.putInstance(KryoRecvHandler.class,new KryoRecvHandler());
            handler.putInstance(HessianRecvHandler.class,new HessianRecvHandler());
            handler.putInstance(ProtostuffRecvHandler.class,new ProtostuffRecvHandler());
    }


    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol) {
            case JDKSERIALIZE: {
                handler.getInstance(JdkNativeRecvHandler.class).handle(handlerMap, pipeline);
                break;
            }
            case KRYOSERIALIZE: {
                handler.getInstance(KryoRecvHandler.class).handle(handlerMap, pipeline);
                break;
            }
            case HESSIANSERIALIZE: {
                handler.getInstance(HessianRecvHandler.class).handle(handlerMap, pipeline);
                break;
            }
            case PROTOSTUFFSERIALIZE: {
                handler.getInstance(ProtostuffRecvHandler.class).handle(handlerMap, pipeline);
                break;
            }
            default: {
                break;
            }
        }
    }
}
