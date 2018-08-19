package com.whoiszxl.netty.websocket;

import java.time.LocalDateTime;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 处理消息的handler
 * @author Administrator
 * TextWebSocketFrame： 在netty中，是用于为websocket专门处理文本的对象
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	/**
	 * 用户记录和管理所有客户端用户的channel组
	 */
	private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		//获取客户端传输过来的消息
		String content = msg.text();
		System.out.println("接收到的数据：" + content);
		
		for (Channel channel : users) {
			channel.writeAndFlush(
					new TextWebSocketFrame(
							"[服务器]在" + LocalDateTime.now() + "接收到消息,消息为：" + content));
			
		}
		
		//直接通过ChannelGroup直接writeAndFlush和用for循环的作用是一样的 
//		users.writeAndFlush(new TextWebSocketFrame(
//				"[服务器]在" + LocalDateTime.now() + "接收到消息,消息为：" + content));
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//在channel生命周期为添加的时候，将其存入到我们自己维护的users的channel组中
		Channel channel = ctx.channel();
		users.add(channel);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
		//users.remove(ctx.channel());  所以这句话是多余的
		
		System.out.println("客户端断开，channel对应的长ID是："+ctx.channel().id().asLongText());
		System.out.println("客户端断开，channel对应的短ID是："+ctx.channel().id().asShortText());
	}
	
	

}
