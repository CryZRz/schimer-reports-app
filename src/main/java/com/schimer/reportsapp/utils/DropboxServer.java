package com.schimer.reportsapp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Arrays;

public class DropboxServer {
    private ServerSocket serverSocket;
    private String receivedCode;
    private final int port;

    public DropboxServer(int port) { this.port = port; }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public String waitForCode() throws IOException {
        try (var socket = serverSocket.accept()) {
            var in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            var requestLine = in.readLine();

            var query = requestLine.split(" ")[1];
            receivedCode = Arrays.stream(query.split("[?&]"))
                    .filter(p -> p.startsWith("code="))
                    .map(p -> p.substring(5))
                    .findFirst().orElse(null);

            var out = new PrintWriter(socket.getOutputStream());
            out.println("HTTP/1.1 200 OK\r\n\r\n");
            out.println("<h2>¡Autenticación exitosa! Puedes cerrar esta pestaña.</h2>");
            out.flush();
        }
        return receivedCode;
    }

    public void stop() throws IOException { serverSocket.close(); }
}
