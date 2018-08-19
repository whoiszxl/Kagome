package com.whoiszxl.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器，channel注册后，会执行里面相对应的初始化方法
 * @author whoiszxl
 *
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		
		//1. 通过SocketChannel获取到对应的管道
		ChannelPipeline pipeline = channel.pipeline();
		//2. 通过管道添加handler
		//2.1 HttpServerCodec是netty自己提供的助手类，可以理解为拦截器
		//2.2 当请求到服务端，我们需要做解码，响应到客户端做编码
		pipeline.addLast("HttpServerCodec", new HttpServerCodec());
		
		//3. 添加一个自定义助手类，返回“hello world”
		pipeline.addLast("customHandler", new CustomHandler());
	}

}
