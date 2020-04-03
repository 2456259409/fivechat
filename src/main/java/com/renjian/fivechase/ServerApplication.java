package com.renjian.fivechase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
    /**
     * 任建
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);
        Server server=new Server(8888);
    }
}
