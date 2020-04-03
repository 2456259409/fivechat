package com.renjian.fivechase;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception{
        Socket socket=new Socket("127.0.0.1",8888);
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write("大家好");
        bufferedWriter.newLine();
        bufferedWriter.flush();

        while (true){}
    }
}
