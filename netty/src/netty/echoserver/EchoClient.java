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
			b.group(group)	// 서버 애플리케이션과 다르게 이븐테 루프 그룹이 하나만 설정되어있다. 서버에 연결된 채널이 하나만 존재하기 때문이다.
				.channel(NioSocketChannel.class)	// 클라이언트 애플리케이션이 생성하는 채널의 종류를 설정한다. NIO로 동작하게 된다.
				.handler(new ChannelInitializer<SocketChannel>() {	
				@Override
				public void initChannel(SocketChannel ch) throws Exception { // 클라이언트 애플리케이션이므로 채널 파이프라인의 설정에 일반 소켓 채널 클래스인 SocketChannel을 설정.
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler());
				}
			});
			ChannelFuture f = b.connect("localhost", 8888).sync();	// 비동기 입출력 메서드인 connect를 호출한다.
			/*
			 	connect 메서드는 메서드의 호출 결과로 ChannelFutrue 객체를 돌려주는데 이 객체를 통해서 비동기 메서드의 처리 결과를 확인할 수 있다.
			 	sync 메서드는 ChannelFutrue 객체의 요청이 완료될 때까지 대기한다.
			 	단, 요청이 실패하면 예외를 던진다.
			 	
			 */
		} finally {
			group.shutdownGracefully();
		}
	}
}
