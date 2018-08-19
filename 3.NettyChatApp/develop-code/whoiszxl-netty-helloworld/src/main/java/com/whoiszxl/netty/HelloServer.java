package com.whoiszxl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author whoiszxl
 * @Description 实现客户端发送一个请求，服务器返回hello world
 *
 */
public class HelloServer {

	public static void main(String[] args) throws InterruptedException {

		// 1. 定义一对线程组
		// 1.1 主线程组，用户接收客户端的连接，但是不做任何处理
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 1.2 从线程组，主线程组会把任务丢给从线程组，让worker处理boss分配的任务
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			//2. netty服务器的创建，ServerBootstrap是一个启动类
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup) //2.1 设置主从线程组
							.channel(NioServerSocketChannel.class)//2.2 设置nio的双向通道
							.childHandler(new HelloServerInitializer()); //2.3 设置子处理器，用于处理workerGroup
			
			//3. 启动server并设置端口为8088.并设置启动方式为同步
			ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
			//4. 监听关闭的channel，设置为同步监听关闭
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}

}
