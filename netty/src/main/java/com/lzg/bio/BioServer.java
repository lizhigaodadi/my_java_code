package com.lzg.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {
        //测试BIO的网络编程
        public static void main(String[] args) throws IOException {
            //创建一个线程池
            ExecutorService executorService = Executors.newCachedThreadPool();

            //创建serverSocket
            ServerSocket serverSocket = new ServerSocket(6666);

            while (true) {  //开始不断的监听端口传递过来的信息
                System.out.println("开始监听端口");
                Socket socket = serverSocket.accept();
                System.out.println("连接上了一个新的客户端");

                //打印当前线程信息
                System.out.println("监听端口线程为"+Thread.currentThread().getName() + "线程id为: "+Thread.currentThread().getId());

                //为每个socket开启一个新的线程
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        handler(socket);
                    }
                });

            }
        }

        public static void handler(Socket socket) {
            //通过输入流接收数据
            String content = "";   //将转化后的数据存储2在这里
            byte[] bytes = new byte[1024];   //用于接收数据
            InputStream inputStream = null;
            int length;

            //打印当前线程信息
            System.out.println("当前线程为："+Thread.currentThread().getName()+"id："+Thread.currentThread().getId());
            try {
                inputStream = socket.getInputStream();
                //将输入流的数据读取出来
                while ((length = inputStream.read(bytes)) != -1) {  //-1的时候就结束了
                    content += new String(bytes,0,length);
                }
                //打印接收的信息
                System.out.println("当前线程接收到的信息为:" + content);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭io流
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
}
