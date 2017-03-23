package netty.echoserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap b = new Bootstrap();
			b.group(group)	// ���� ���ø����̼ǰ� �ٸ��� �̺��� ���� �׷��� �ϳ��� �����Ǿ��ִ�. ������ ����� ä���� �ϳ��� �����ϱ� �����̴�.
				.channel(NioSocketChannel.class)	// Ŭ���̾�Ʈ ���ø����̼��� �����ϴ� ä���� ������ �����Ѵ�. NIO�� �����ϰ� �ȴ�.
				.handler(new ChannelInitializer<SocketChannel>() {	
				@Override
				public void initChannel(SocketChannel ch) throws Exception { // Ŭ���̾�Ʈ ���ø����̼��̹Ƿ� ä�� ������������ ������ �Ϲ� ���� ä�� Ŭ������ SocketChannel�� ����.
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler());
				}
			});
			ChannelFuture f = b.connect("localhost", 8888).sync();	// �񵿱� ����� �޼����� connect�� ȣ���Ѵ�.
			/*
			 	connect �޼���� �޼����� ȣ�� ����� ChannelFutrue ��ü�� �����ִµ� �� ��ü�� ���ؼ� �񵿱� �޼����� ó�� ����� Ȯ���� �� �ִ�.
			 	sync �޼���� ChannelFutrue ��ü�� ��û�� �Ϸ�� ������ ����Ѵ�.
			 	��, ��û�� �����ϸ� ���ܸ� ������.
			 	
			 */
		} finally {
			group.shutdownGracefully();
		}
	}
}
