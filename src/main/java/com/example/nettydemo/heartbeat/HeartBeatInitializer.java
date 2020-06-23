package com.example.nettydemo.heartbeat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Desc:
 * @Author: MrDi
 * @Date: 2020/6/18 14:50
 */
public class HeartBeatInitializer extends ChannelInitializer<Channel> {
    private static  final int READ_TIME_OUT = 4;
    private static  final int WRITE_TIME_OUT = 5;
    private static  final int ALL_TIME_OUT = 7;
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateHandler(READ_TIME_OUT,WRITE_TIME_OUT,ALL_TIME_OUT, TimeUnit.SECONDS))
                .addLast(new HeartBeatHandler());
    }
}
