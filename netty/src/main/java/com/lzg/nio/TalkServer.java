package com.lzg.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

//群聊系统的服务器端
public class TalkServer {
    private static final int port = 6667;   //监听的端口号
    private static ServerSocketChannel serverSocketChannel;
    private static Selector selector;

    /**
     * 开启服务器的服务
     */
    public static void openServer() {
        //赋值给ServerSocketChannel
        try {
            serverSocketChannel = ServerSocketChannel.open();
            //赋值给selector
            selector = Selector.open();
            //绑定端口号、选择器和事件行为，开启非阻塞模式
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //开启事件的监听
            listen();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //开启事件的监听
    public static void listen() {
        while (true) {

            try {
                //首先判断是否有客户端连接上来
                if (selector.select(1000) == 0) {
                    //发现当前并没有人连接上来
                    System.out.println("最近1s内无客户端连接");
                    continue;   //结束重新来
                }
                //获取SelectionKeys的集合
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //获取迭代器
                Iterator<SelectionKey> keyIterator = selectionKeys.iterator();

                //开始对不同的情况做出不同的处理
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    //分别进行判断
                    if (selectionKey.isAcceptable()) {
                        SocketChannel channel = serverSocketChannel.accept();
                        channel.configureBlocking(false);   //设置为非堵塞模式
                        String message = channel.getRemoteAddress() + "上线了";
                        //告诉别人老子上线了
                        talkToOthers(message,channel);

                        //设置缓冲区和通道的相互关联
                        channel.register(selector,SelectionKey.OP_READ);  //不绑定缓冲区，要用的时候自己声明

                    }

                    if (selectionKey.isReadable()) {
                        //有用户发送了消息过来
                        readData(selectionKey);
                    }

                    //将当前对象从迭代器移除
                    keyIterator.remove();
                }

                SocketChannel socketChannel = serverSocketChannel.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 向别人发送消息
     * @param message 要发送的内容
     * @param currentChannel 当前的channel
     */
    public static void talkToOthers(String message,SocketChannel currentChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);   //共用同一个buffer
        //获取所有的key
        Set<SelectionKey> keys = selector.keys();

        //遍历每一个key，然后发送消息，如果是当前的Channel就不用发送消息了
        for(SelectionKey key : keys) {
            //获取通道
            Channel channel = key.channel();  //这里有可能是ServerSocketChannel，所以使用最底层的接口来代替强转
            //通过地址判断是否是同一个
            if (channel instanceof SocketChannel && currentChannel != channel) {
                SocketChannel sc = (SocketChannel) channel;

                //开始发送消息,在通道中写入数据
                buffer.put(message.getBytes(StandardCharsets.UTF_8));
                sc.write(buffer);
                //清空buffer
                buffer.clear();
            }


        }
    }

    public static void readData(SelectionKey key) throws IOException {
        //获取通道
        SocketChannel channel = (SocketChannel) key.channel();
        //从通道中读取数据
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);

        //将读取的数据发送出去
        talkToOthers(channel.getRemoteAddress() + "说：" + new String(buffer.array()),channel);
    }


    public static void main(String[] args) {
        openServer();
    }
}
