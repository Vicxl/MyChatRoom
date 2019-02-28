package com.xloo1.Multi;

import java.io.IOException;
import java.net.Socket;

public class MultiTreadClint {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",6666);
            //1.从服务器发送数据
            new WriteDataToServerThread(socket).start();
            //2.从服务器读取数据
            new ReadDataFromServerThread(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
