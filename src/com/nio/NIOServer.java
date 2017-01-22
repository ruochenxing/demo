package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {

	// 通道选择器
	private Selector selector;

	public void initServer(int port) throws IOException {
		// 获取一个ServerSocket通道
		ServerSocketChannel channel = ServerSocketChannel.open();
		// 设置通道为非阻塞
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress(port));// 绑定端口
		this.selector = Selector.open();// 获取通道选择器
		//注册请求事件
		channel.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void listen() throws IOException {
		System.out.println("server start....");
		// 轮询选择器
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 当注册事件达到时，方法返回；否则阻塞
			selector.select();
			// 遍历已选择的键集合来访问就绪的通道
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = ite.next();
				ite.remove();// 删除
				// 客户端请求连接事件
				if (key.isAcceptable()) {
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					// 获取和客户端连接的channel
					SocketChannel channel = ssc.accept();
					// 设置成非阻塞
					channel.configureBlocking(false);
					// 返回信息给客户端
					channel.write(ByteBuffer.wrap(new String("acceptable msg,response of server ").getBytes()));
					// 在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
					channel.register(this.selector, SelectionKey.OP_READ);
				} else {// 如果是可读事件
					read(key);
				}
			}
		}
	}

	private void read(SelectionKey key) throws IOException {
		// 获取和客户端连接的channel
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建读取的缓存区
		ByteBuffer buffer = ByteBuffer.allocate(100);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("get msg from client:" + msg);
		ByteBuffer response = ByteBuffer.wrap(msg.getBytes());
		channel.write(response);// 将消息回送给客户端
	}
	
	public static void main(String[] args) throws IOException{
		NIOServer server=new NIOServer();
		server.initServer(8000);
		server.listen();
	}
}
