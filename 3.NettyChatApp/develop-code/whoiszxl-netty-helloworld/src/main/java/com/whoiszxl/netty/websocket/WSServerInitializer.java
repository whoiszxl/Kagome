package com.whoiszxl.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * 初始化器，channel注册后，会执行里面相对应的初始化方法
 * @author whoiszxl
 *
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		//================用于支持http协议=====================
		ChannelPipeline pipeline = ch.pipeline();
		//websocket基于http协议，所以需要http的编解码器
		pipeline.addLast(new HttpServerCodec());
		//对写大数据流的支持
		pipeline.addLast(new ChunkedWriteHandler());
		// 对httpmessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
		// 几乎在netty编程中都会用到这个handler
		pipeline.addLast(new HttpObjectAggregator(1024*64));
		
		
		/**
		 * websocket服务器处理的协议，用于指定给客户端连接访问的路由：/ws
		 * 这个handler会处理一些繁重的事情
		 * 处理握手动作：handshaking(close,ping,pong) ping+pong=心跳机制
		 * 对于websocket来讲，都是以frames进行传输，不同数据类型对应的frames也不同
		 */
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		
		//自定义handler
		pipeline.addLast(new ChatHandler());
	}

}
