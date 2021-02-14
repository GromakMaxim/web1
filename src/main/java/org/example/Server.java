package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private int PORT = 29999;
    private int THREADSNUMBER = 64;

    public void start() throws IOException {
        Socket clientSocket = null;
        final ServerSocket serverSocket = new ServerSocket(PORT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREADSNUMBER);
        try {
            while (true) {
                clientSocket = serverSocket.accept();
                executorService.execute(new HandlerConnection(clientSocket, serverSocket));
            }
        } finally {
            stop(clientSocket, serverSocket);
            System.out.println("Сервер остановлен");
        }

    }
    public void stop(Socket clientSocket, ServerSocket serverSocket) throws IOException {
        clientSocket.close();
        serverSocket.close();
    }

}
