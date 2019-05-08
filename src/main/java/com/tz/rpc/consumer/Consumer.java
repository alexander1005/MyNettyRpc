package com.tz.rpc.consumer;

import com.tz.rpc.api.Cal;
import com.tz.rpc.api.SayHello;
import com.tz.rpc.consumer.proxy.MyProxy;
import com.tz.rpc.provider.Provider;

import java.lang.reflect.Proxy;

public class Consumer {


    public static void main(String[] args) throws InstantiationException, IllegalAccessException {

//
//        SayHello sayHello  =MyProxy.create(SayHello.class);
//
//
//        String hello = sayHello.hello(" suzhiqi ");
//
//        System.out.println("调用成功"+ hello);


        Cal cal = MyProxy.create(Cal.class);

        //System.out.println(cal.add(12,12));
        Integer sub = cal.sub(12, 12);
        System.out.println(sub);
        System.out.println(cal.add(12,12));
        System.out.println(cal.add(15,17));

    }
}
