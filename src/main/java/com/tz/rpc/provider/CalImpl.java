package com.tz.rpc.provider;

import com.tz.rpc.api.Cal;

public class CalImpl implements Cal {
    @Override
    public Integer add(Integer i1, Integer i2) {

        return i1+i2;
    }

    @Override
    public Integer sub(Integer i1, Integer i2) {

        return i1-i2;
    }
}
