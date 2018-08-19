package com.whoiszxl.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author Administrator
 *
 */
public class WSServer {

	
	public static void main(String[] args) throws InterruptedException {
		
		//1. 定义一对线程组
		EventLoopGroup mainGroup = new NioEventLoopGroup();
		EventLoopGroup subGroup = new NioEventLoopGroup();
		
		try {
			//2. 定义启动类
			ServerBootstrap server = new ServerBootstrap();
			server.group(mainGroup, subGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new WSServerInitializer());
			
			ChannelFuture channelFuture = server.bind(8088).sync();
			
			channelFuture.channel().closeFuture().sync();
		} finally {
			mainGroup.shutdownGracefully();
			subGroup.shutdownGracefully();
		}
	}
}
