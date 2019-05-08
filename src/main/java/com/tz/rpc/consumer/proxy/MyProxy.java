package com.tz.rpc.consumer.proxy;

import com.tz.rpc.handler.RegistryHandlers;
import com.tz.rpc.msg.InvokerMsg;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy {


    public static <T> T create(Class<?> clazz) throws IllegalAccessException, InstantiationException {

        MethodProxy proxy = new MethodProxy(clazz);

        T o = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, proxy);

        return o ;

    }



}

class MethodProxy implements InvocationHandler{

    private Class<?> clazz;

    MethodProxy(Class<?> clazz){

        this.clazz=  clazz;
    }



    //发起网络请求
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //如果传进来是已经实现具体的类 直接忽略
        if (Object.class.equals(method.getDeclaringClass()))
            return method.invoke(this,args);

        //远程调用

        return invokeRemote(method,args);

    }

    private Object invokeRemote(Method method, Object[] args) throws InterruptedException {

        InvokerMsg msg = new InvokerMsg();

        msg.setClassName(clazz.getName());
        msg.setMethodName(method.getName());
        Class<?>[] classes  =new Class[args.length];
        for(int i =0 ;i<args.length;i++){

           classes[i]=args[i].getClass();

        }
        msg.setValues(args);
        msg.setParames(classes);
        EventLoopGroup group = new NioEventLoopGroup();
        final ProxyHandlers proxyHandlers = new ProxyHandlers();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).
            channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {

                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            pipeline.addLast("encode",new ObjectEncoder());
                            pipeline.addLast("decode",new ObjectDecoder(ClassResolvers.cacheDisabled(null)));

                            //业务
                            pipeline.addLast("hadnler",proxyHandlers);
                        }
                    });

            ChannelFuture f = bootstrap.connect("localhost", 8081).sync();
            f.channel().writeAndFlush(msg).sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

        return proxyHandlers.getResult();
    }
}
