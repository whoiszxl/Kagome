package com.whoiszxl.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * ��ʼ������channelע��󣬻�ִ���������Ӧ�ĳ�ʼ������
 * @author whoiszxl
 *
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		
		//1. ͨ��SocketChannel��ȡ����Ӧ�Ĺܵ�
		ChannelPipeline pipeline = channel.pipeline();
		//2. ͨ���ܵ����handler
		//2.1 HttpServerCodec��netty�Լ��ṩ�������࣬�������Ϊ������
		//2.2 �����󵽷���ˣ�������Ҫ�����룬��Ӧ���ͻ���������
		pipeline.addLast("HttpServerCodec", new HttpServerCodec());
		
		//3. ���һ���Զ��������࣬���ء�hello world��
		pipeline.addLast("customHandler", new CustomHandler());
	}

}
