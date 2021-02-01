package com.liuyang.io.netty.pojo;

import lombok.Data;

public class Protocol {
    private int length;
    private byte[] data;

    public Protocol(byte[] data) {
        this.data = data;
        this.length = data.length;
    }

    public int getLength() {
        return length;
    }

    public Protocol setLength(int length) {
        this.length = length;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public Protocol setData(byte[] data) {
        this.data = data;
        return this;
    }
}
