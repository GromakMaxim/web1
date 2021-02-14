package org.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) throws Throwable {
        ServerSocket serverSocket = new ServerSocket(25999);
        int threadsNumber = 64;
        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);
        while (true) {
            Socket s = serverSocket.accept();
            System.err.println("Client accepted");

            executorService.execute(new Server(s));
        }
    }


}