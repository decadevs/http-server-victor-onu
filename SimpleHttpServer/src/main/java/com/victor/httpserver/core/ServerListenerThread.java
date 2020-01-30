package com.victor.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webroot;
    ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }



    @Override
    public void run() {

        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                LOGGER.info(" * Connection accepted " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

//                InputStream inputStream = socket.getInputStream();
//                OutputStream outputStream = socket.getOutputStream();
//
//
//                String html = "<html><head><title> Simple Java HTTP Server</title></head><body><h1>This page was served with my simple Java HTTP Server</h1></body></html>";
//
//                final String CRLF = "\n\r"; //13, 10
//
//                String response = "HTTP/1.1 OK " + CRLF + // STATUS LINE: HTTP VERSION_RESPONSE CODE RESPONSE MESSAGE
//                        "Content-Length: " + html.getBytes().length + CRLF + //HEADER
//                        CRLF +
//                        html +
//                        CRLF + CRLF;
//
//                outputStream.write(response.getBytes());
//
//                inputStream.close();
//                outputStream.close();
//                socket.close();

            }
//            serverSocket.close(); //to handle later

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
