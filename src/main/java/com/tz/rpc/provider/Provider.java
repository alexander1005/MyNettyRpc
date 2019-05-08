package com.tz.rpc.provider;

import com.tz.rpc.api.SayHello;


public class Provider implements SayHello {
    public String hello(String name) {
        System.out.println("服务端say" + name);
        return "hello " +name;
    }
}
