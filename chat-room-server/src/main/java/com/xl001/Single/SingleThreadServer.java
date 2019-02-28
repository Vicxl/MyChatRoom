package com.xl001.Single;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SingleThreadServer {
    public static void main(String[] args){
        try {
            //通过命令行获取客户端的端口号
            int port = 6666;
            if(args.length>0){
                try{
                    port = Integer.parseInt(args[0]);
                }catch(NumberFormatException e){
                    System.out.println("端口参数不正确，采用默认端口"
                            + port);
                }
            }

            //1.创建ServerSocket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务器启动："
                    + serverSocket.getLocalSocketAddress());

            //2.等待客户端连接
            System.out.println("等待客户端连接...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端的信息：" + clientSocket.getLocalSocketAddress());

            //3.接收数据
            InputStream clientInput = clientSocket.getInputStream();
            Scanner scanner = new Scanner(clientInput);
            String clientData = scanner.next();
            System.out.println("来自客户端的信息：" + clientData);

            //4.发送数据
            OutputStream clientOutput = clientSocket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write("你好，欢迎连接服务器...\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
