package com.victor.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title> Simple Java HTTP Server</title></head><body><h1>This page was served with my simple Java HTTP Server</h1></body></html>";

            final String CRLF = "\n\r"; //13, 10

            String response = "HTTP/1.1 OK " + CRLF + // STATUS LINE: HTTP VERSION_RESPONSE CODE RESPONSE MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF + //HEADER
                    CRLF +
                    html +
                    CRLF + CRLF;

            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LOGGER.info("Processing finished");

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
