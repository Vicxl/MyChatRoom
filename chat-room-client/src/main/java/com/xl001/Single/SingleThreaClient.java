//package com.xl001.Single;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class SingleThreaClient {
//    public static void main(String[] args) {
//            //通过命令行获取客户端的端口号
//            int port = 6666;
//            if(args.length>0){
//                try{
//                    port = Integer.parseInt(args[0]);
//                }catch(NumberFormatException e){
//                    System.out.println("端口参数不正确，采用默认端口"
//                            + port);
//                }
//            }
//            String host = "127.0.0.1";
//            if(args.length > 1){
//                host = args[1];
//            }
//
//                //1.创建客户端，连接到服务器
//            try {
//                Socket clientSocker = new Socket(host,port);
//                //2.发送数据
//                OutputStream clientOutput = clientSocker.getOutputStream();
//                OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
//                writer.write("你好，我是客户端\n");
//                writer.flush();
//                //3.接收数据
//                InputStream clientInput = clientSocker.getInputStream();
//                Scanner scanner = new Scanner(clientInput);
//                String serverData = scanner.nextLine();
//                System.out.println("来自客户端的数据：" + serverData);
//                //关闭客户端
//                clientInput.close();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
