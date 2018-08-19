package com.whoiszxl.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * ��ʼ������channelע��󣬻�ִ���������Ӧ�ĳ�ʼ������
 * @author whoiszxl
 *
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel>{

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		//================����֧��httpЭ��=====================
		ChannelPipeline pipeline = ch.pipeline();
		//websocket����httpЭ�飬������Ҫhttp�ı������
		pipeline.addLast(new HttpServerCodec());
		//��д����������֧��
		pipeline.addLast(new ChunkedWriteHandler());
		// ��httpmessage���оۺϣ��ۺϳ�FullHttpRequest��FullHttpResponse
		// ������netty����ж����õ����handler
		pipeline.addLast(new HttpObjectAggregator(1024*64));
		
		
		/**
		 * websocket�����������Э�飬����ָ�����ͻ������ӷ��ʵ�·�ɣ�/ws
		 * ���handler�ᴦ��һЩ���ص�����
		 * �������ֶ�����handshaking(close,ping,pong) ping+pong=��������
		 * ����websocket������������frames���д��䣬��ͬ�������Ͷ�Ӧ��framesҲ��ͬ
		 */
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		
		//�Զ���handler
		pipeline.addLast(new ChatHandler());
	}

}
