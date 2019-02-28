package com.xl001.Multi.Server;

import javax.activation.DataHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

class ExcuteClient implements Runnable{
    private static final Map<String,Socket> ONLINE_USER_MAP = new
            ConcurrentHashMap<String,Socket>();
    private final Socket client;

    ExcuteClient(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            InputStream clientInput = this.client.getInputStream();
            Scanner scanner = new Scanner(clientInput);
            String line = scanner.nextLine();
            //注册 userName:<name>
            if (line.startsWith("userName")) {
                String userName = line.split("\\:")[1];
                this.register(userName, client);
            }
            if (line.startsWith("private")) {
                String[] segments = line.split("\\:");
                String userName = segments[1];
                String message = segments[2];
                this.privateChat(userName, message);
            }
            if (line.startsWith("group")) {
                String[] message = line.split("\\:");
                this.groupChat(message);

            }
            if (line.equals("bye")) {
                this.quit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void quit(){
        String currentUserName = this.getCurrentUserName();
        System.out.println("用户：" + currentUserName + "下线");
        Socket socket = ONLINE_USER_MAP.get(currentUserName);
        this.sendMessage(socket,"bye");
        ONLINE_USER_MAP.remove(currentUserName);
        printOnlineUser();
    }

    private String getCurrentUserName() {
        String currentUserName = "";
        for(Map.Entry<String,Socket> entry : ONLINE_USER_MAP.entrySet()){
            if(this.client.equals(entry.getValue())){
                currentUserName = entry.getKey();
                break;
            }
        }
    }

    private void groupChat(String[] message) {
        for(Socket socket:ONLINE_USER_MAP.values()){
            this.sendMessage(socket,this.getCurrentUserName()
                    + "说：" + message);
        }
    }

    private void privateChat(String userName, String message) {
        String currentUserName = this.getCurrentUserName();
        Socket target = ONLINE_USER_MAP.get(userName);
        if(target != null){
            this.sendMessage(target,currentUserName +"对你说" + message);
        }
    }

    private void register(String userName, Socket client) {
        System.out.println(userName + "加入到聊天室"
                + client.getLocalSocketAddress());
        ONLINE_USER_MAP.put(userName,client);
        printOnlineUser();
        sendMessage(this.client,userName+"注册成功");
    }

    private void sendMessage(Socket client, String s) {
        try{
            OutputStream clientOutput = client.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(clientOutput);
            writer.write(s+"\n");
            writer.flush();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    private void printOnlineUser() {
        System.out.println("当前在线人数：" + ONLINE_USER_MAP.size()
                + "用户如下所示：");
        for(Map.Entry<String, Socket> entry:ONLINE_USER_MAP.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
