package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HandlerConnection implements Runnable {
    private Socket clientSocket;
    private ServerSocket serverSocket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public HandlerConnection(Socket clientSocket, ServerSocket serverSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            readInputHeaders();
            showMainPage();
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private void writeResponse(String s) throws Throwable {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Server: GromakServer/2021-02-13\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + s.length() + "\r\n" +
                "Connection: close\r\n\r\n";
        String result = response + s;
        outputStream.write(result.getBytes());
        outputStream.flush();
    }

    private void readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            String s = br.readLine();
            if (s == null || s.trim().length() == 0) {
                break;
            }
            System.out.println(s);
            if (s.contains("/page1.html")) {
                showPage1();
            } else if (s.contains("/page2.html")) {
                showPage2();
            } else if (s.contains("/main.html")) {
                showMainPage();
            }

        }
        System.out.println("-------------------------------------------------");
    }

    private void showPage1() throws Throwable {
        writeResponse("" +
                "<html>" +
                "   <body>" +
                "       <h1>page 1</h1>" +
                "       <a href=\\page2.html>to go on page 2</a> <br>" +
                "<a href=\\main.html>return to main</a>" +
                "       <style type=\"text/css\">\n" +
                "           h1 { \n" +
                "               font-size: 25px; \n" +
                "               font-family: Verdana, Arial, Helvetica, sans-serif; \n" +
                "               color: #333366;\n" +
                "           }\n" +
                "           a {\n" +
                "               text-decoration: none;" +
                "           }\n" +
                "        </style>" +
                "   </body>" +
                "</html>");
    }

    private void showPage2() throws Throwable {
        writeResponse("" +
                "<html>" +
                "   <body>" +
                "       <h1>page 2</h1>" +
                "       <a href=\\page1.html>to go on page 1</a><br>" +
                "       <a href=\\main.html>return to main</a>" +
                "       <style type=\"text/css\">\n" +
                "           h1 { \n" +
                "               font-size: 25px; \n" +
                "               font-family: Verdana, Arial, Helvetica, sans-serif; \n" +
                "               color: #333366;\n" +
                "           }\n" +
                "           a {\n" +
                "               text-decoration: none;" +
                "           }\n" +
                "        </style>" +
                "   </body>" +
                "</html>");
    }

    private void showMainPage() throws Throwable {
        writeResponse("" +
                "<html>" +
                "   <body>" +
                "       <h1>Main page</h1>" +
                "       <a href=\\page1.html>to go on page 1</a><br>" +
                "       <a href=\\page2.html>to go on page 2</a>" +
                "       <style type=\"text/css\">\n" +
                "           h1 { \n" +
                "               font-size: 25px; \n" +
                "               font-family: Verdana, Arial, Helvetica, sans-serif; \n" +
                "               color: #333366;\n" +
                "           }\n" +
                "           a {\n" +
                "               text-decoration: none;" +
                "           }\n" +
                "        </style>" +
                "   </body>" +
                "</html>");
    }
}
