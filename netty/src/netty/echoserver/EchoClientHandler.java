package netty.echoserver;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) {	// ChannelInboundHanlder�� ���ǵ� �̺�Ʈ�ν� ���� ä���� ���� Ȱ��ȭ �ɶ� �����.
		String sendMessage = "Hello, Netty!";

		ByteBuf messageBuffer = Unpooled.buffer();
		messageBuffer.writeBytes(sendMessage.getBytes());

		StringBuilder builder = new StringBuilder();
		builder.append("������ ���ڿ� [");
		builder.append(sendMessage);
		builder.append("]");

		System.out.println(builder.toString());
		ctx.writeAndFlush(messageBuffer);	// ���������� ������ ��ϰ� ������ �� ���� �޼��带 ȣ����. �����͸� ����ϴ� �޼��� write�� �޽����� ������ �����ϴ� flush �޼����̴�.
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // �����κ��� ���ŵ� �����Ͱ� ���� �� ȣ��Ǵ� ��Ƽ �̺�Ʈ �޼����.
		String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());	// �����κ��� ���ŵ� �����Ͱ� ����� msg��ü���� ���ڿ� ����.

		StringBuilder builder = new StringBuilder();
		builder.append("������ ���ڿ�");
		builder.append(readMessage);
		builder.append("]");

		System.out.println(builder.toString());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) { // ���ŵ� �����͸� ��� �о��� �� ȣ��Ǵ� �̺�Ʈ �޼���. channelRead �޼����� ������ �Ϸ�ǰ� �����.
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {	// ���ŵ� �����͸� ��� ���� �� ������ ����� ä���� �ݴ´�. ������ �ۼ��ŵ� ä���� ������
																				// Ŭ���̾�Ʈ ���α׷��� ����ȴ�.
		cause.printStackTrace();
		ctx.close();
	}
}
