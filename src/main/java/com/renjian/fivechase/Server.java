package com.renjian.fivechase;

import com.renjian.fivechase.model.User;
import com.renjian.fivechase.util.ChatWithServer;
import com.renjian.fivechase.util.MyExecutor;
import com.renjian.fivechase.util.UserSerialize;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

public class Server{

    private boolean isRunning=true;
    private ServerSocket serverSocket=null;
    private ThreadPoolExecutor executor = MyExecutor.getExecutor();
    private List<ClientConnect> connets=new ArrayList<>();
    ChatWithServer chat = null;
    public Server () {}
    public Server (int port) {
        try {
            serverSocket=new ServerSocket(port);
            while(isRunning) {
                Socket socket=serverSocket.accept();
                System.out.println(socket.getInetAddress()+"/"+socket.getPort()+"连接上服务器");
                DataInputStream dis=new DataInputStream(socket.getInputStream());
                String ustr=dis.readUTF();
                User user = UserSerialize.getSerialize(ustr);
                ClientConnect clientConn = new ClientConnect(socket, user);
                clientConn.SendTipToAll("恭喜"+clientConn.getUser().getNickname()+"成功登录!\n\n");
                connets.add(clientConn);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class ClientConnect implements Runnable{
        private Socket socket=null;
        private DataInputStream dis=null;
        private User user=null;
        private boolean isRun=true;
        public User getUser(){
            return user;
        }

        public Socket getSocket(){
            return socket;
        }
        public ClientConnect() {}

        public ClientConnect(Socket socket,User user) {
            this.user=user;
            this.socket=socket;
            CompletableFuture.runAsync(()->{
                run();
            },executor);
        }

        @Override
        public void run() {
            try {
                while(isRun) {
                    String str="";
                    if(!this.socket.isClosed()){
                        dis=new DataInputStream(socket.getInputStream());
                        str=dis.readUTF();
                    }
                    if(str.startsWith("Close")){
                        for(ClientConnect connect:connets){
                            if(connect==this){
                                Socket socket=this.getSocket();
                                System.out.println(socket.getInetAddress()+"/"+socket.getPort()+" :执行了关闭操作!");
                                connect.SendTipToAll(connect.getUser().getNickname()+"断开连接!\n\n");
                                connets.remove(this);
                                socket.close();
                                this.dis.close();
                                isRun=false;
                                break;
                            }
                        }
                    }else{
                        System.out.println(str);
                        SendToAll(str);
                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        public void SendToAll(String msg){
            for(ClientConnect connect:connets){
                if(connect!=this){
                    chat=new ChatWithServer(connect.getSocket());
                    try {
                        chat.SendMsg(this.getUser().getNickname()+"说:"+msg+"\n\n");
                    } catch (Exception e) {
                        System.out.println("奇怪的错误");
                    }
                }
            }
        }

        public void SendTipToAll(String msg){
            for(ClientConnect connect:connets){
                if(connect!=this){
                    chat=new ChatWithServer(connect.getSocket());
                    try {
                        chat.SendMsg(msg);
                    } catch (Exception e) {
                        System.out.println("奇怪的错误");
                    }
                }
            }
        }
    }


}
