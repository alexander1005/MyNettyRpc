package com.tz.rpc.handler;

import com.tz.rpc.msg.InvokerMsg;
import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryHandlers extends ChannelInboundHandlerAdapter {


    public List<String> classCache;
    public static ConcurrentHashMap<String, Object> registryMap;

    public RegistryHandlers() {

        classCache = new ArrayList<String>();
        registryMap = new ConcurrentHashMap();
        scanClass("com.tz.rpc.provider");
        try {
            doRegister();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        Object result = new Object();

        InvokerMsg request  = (InvokerMsg) msg;

        if (registryMap.containsKey(request.getClassName())){

            Object obj = registryMap.get(request.getClassName());

            Method method = obj.getClass().getMethod(request.getMethodName(), request.getParames());
            method.setAccessible(true);
            Object invoke = method.invoke(obj, request.getValues());
            result = invoke;
        }

        ctx.writeAndFlush(result);
        ctx.close();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }


    void scanClass(String packageName) {


        URL resource = this.getClass().getClassLoader().getResource(packageName.replace(".", "/"));
        File dir = new File(resource.getFile());
        for (File file : dir.listFiles()) {

            if (file.isDirectory()) {

                scanClass(packageName + "." + file.getName());
            } else {

                String className = packageName + "." + file.getName().replace(".class", "").trim();

                classCache.add(className);
            }


        }
    }


    void doRegister() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        if (classCache.size() == 0) return;

        for(String className : classCache){


            Class<?> aClass = Class.forName(className);
            //服务名
            Class<?> inteface  = aClass.getInterfaces()[0];

            registryMap.put(inteface.getName(),aClass.newInstance());
        }

    }
}
