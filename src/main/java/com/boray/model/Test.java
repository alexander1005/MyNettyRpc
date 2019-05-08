package com.boray.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Test implements Serializable {

    private byte[] bytes;

    public Test(int kb) {

        bytes = new byte[kb*1024];

        for (int i =0 ;i<bytes.length;i++){
            bytes[i] = 123;
        }
    }

    public byte[] getBytes() {
        return bytes;
    }
}
