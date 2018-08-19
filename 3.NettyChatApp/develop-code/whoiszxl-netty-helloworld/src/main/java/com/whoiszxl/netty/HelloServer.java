package com.whoiszxl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @author whoiszxl
 * @Description ʵ�ֿͻ��˷���һ�����󣬷���������hello world
 *
 */
public class HelloServer {

	public static void main(String[] args) throws InterruptedException {

		// 1. ����һ���߳���
		// 1.1 ���߳��飬�û����տͻ��˵����ӣ����ǲ����κδ���
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// 1.2 ���߳��飬���߳��������񶪸����߳��飬��worker����boss���������
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			//2. netty�������Ĵ�����ServerBootstrap��һ��������
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup) //2.1 ���������߳���
							.channel(NioServerSocketChannel.class)//2.2 ����nio��˫��ͨ��
							.childHandler(new HelloServerInitializer()); //2.3 �����Ӵ����������ڴ���workerGroup
			
			//3. ����server�����ö˿�Ϊ8088.������������ʽΪͬ��
			ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
			//4. �����رյ�channel������Ϊͬ�������ر�
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}

}
