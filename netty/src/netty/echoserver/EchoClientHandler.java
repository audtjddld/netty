package netty.echoserver;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {	// ChannelInboundHanlder에 정의된 이벤트로써 소켓 채널이 최초 활성화 될때 실행됨.
		String sendMessage = "Hello, Netty!";

		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(sendMessage.getBytes());

		StringBuilder builder = new StringBuilder();
		builder.append("전송한 문자열 [");
		builder.append(sendMessage);
		builder.append("]");

		System.out.println(builder.toString());
		ctx.writeAndFlush(messageBuffer);	// 내부적으로 데이터 기록과 전송의 두 가지 메서드를 호출함. 데이터를 기록하는 메서드 write와 메시지를 서버로 전송하는 flush 메서드이다.
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // 서버로부터 수신된 데이터가 있을 때 호출되는 네티 이벤트 메서드다.
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());	// 서버로부터 수신된 데이터가 저장된 msg객체에서 문자열 추출.

		StringBuilder builder = new StringBuilder();
		builder.append("수신한 문자열");
		builder.append(readMessage);
		builder.append("]");

		System.out.println(builder.toString());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) { // 수신된 데이터를 모두 읽었을 때 호출되는 이벤트 메서드. channelRead 메서드의 수행이 완료되고 실행됨.
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {	// 수신된 데이터를 모두 읽은 후 서버와 연결된 채널을 닫는다. 데이터 송수신된 채널을 닫히고
																				// 클라이언트 프로그램은 종료된다.
		cause.printStackTrace();
		ctx.close();
	}
}
