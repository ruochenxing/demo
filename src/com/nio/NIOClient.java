package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {

	private Selector selector;

	public void initClient(String ip, int port) throws IOException {
		SocketChannel channel = SocketChannel.open();
		this.selector = Selector.open();

		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress(ip, port));
		// 注册连接事件
		channel.register(selector, SelectionKey.OP_CONNECT);
	}

	// 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理
	public void listen() throws IOException {
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			selector.select();
			// 遍历已选择的键集合来访问就绪的通道
			Iterator<SelectionKey> ite = this.selector.selectedKeys().iterator();
			// 连接事件
			if (ite.hasNext()) {
				SelectionKey key = ite.next();
				ite.remove();
				// 如果是连接事件
				if (key.isConnectable()) {
					SocketChannel channel = (SocketChannel) key.channel();
					// 如果正在连接，则完成连接
					if (channel.isConnectionPending()) {
						channel.finishConnect();
					}
					channel.configureBlocking(false);
					channel.write(ByteBuffer.wrap(new String("send msg to server ").getBytes()));
					// 在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。注册读事件
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					read(key);
				}
			}
		}
	}

	private void read(SelectionKey key) throws IOException {
		// 获取和服务器连接的channel
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建读取的缓存区
		ByteBuffer buffer = ByteBuffer.allocate(100);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("get msg from server:" + msg);
		ByteBuffer response = ByteBuffer.wrap(msg.getBytes());
		channel.write(response);// 将消息回送给服务器
	}

	/**
	 * 启动客户端测试
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		NIOClient client = new NIOClient();
		client.initClient("localhost", 8000);
		client.listen();
	}
}
