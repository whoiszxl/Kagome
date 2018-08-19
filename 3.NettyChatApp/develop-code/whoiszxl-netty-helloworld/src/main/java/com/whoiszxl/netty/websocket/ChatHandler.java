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
 * ������Ϣ��handler
 * @author Administrator
 * TextWebSocketFrame�� ��netty�У�������Ϊwebsocketר�Ŵ����ı��Ķ���
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	/**
	 * �û���¼�͹������пͻ����û���channel��
	 */
	private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		//��ȡ�ͻ��˴����������Ϣ
		String content = msg.text();
		System.out.println("���յ������ݣ�" + content);
		
		for (Channel channel : users) {
			channel.writeAndFlush(
					new TextWebSocketFrame(
							"[������]��" + LocalDateTime.now() + "���յ���Ϣ,��ϢΪ��" + content));
			
		}
		
		//ֱ��ͨ��ChannelGroupֱ��writeAndFlush����forѭ����������һ���� 
//		users.writeAndFlush(new TextWebSocketFrame(
//				"[������]��" + LocalDateTime.now() + "���յ���Ϣ,��ϢΪ��" + content));
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//��channel��������Ϊ��ӵ�ʱ�򣬽�����뵽�����Լ�ά����users��channel����
		Channel channel = ctx.channel();
		users.add(channel);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//������handlerRemoved��ChannelGroup���Զ��Ƴ���Ӧ�ͻ��˵�channel
		//users.remove(ctx.channel());  ������仰�Ƕ����
		
		System.out.println("�ͻ��˶Ͽ���channel��Ӧ�ĳ�ID�ǣ�"+ctx.channel().id().asLongText());
		System.out.println("�ͻ��˶Ͽ���channel��Ӧ�Ķ�ID�ǣ�"+ctx.channel().id().asShortText());
	}
	
	

}
