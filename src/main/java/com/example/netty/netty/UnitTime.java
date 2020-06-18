package com.example.netty.netty;

import java.util.Date;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/17 14:24
 */
public class UnitTime {
    private final long value;

    public UnitTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public UnitTime(long value) {
        this.value = value;
    }

    public long value(){
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}
