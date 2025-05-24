package Networking.Exercise4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    int port;
    String logFilePath;

    public Server(int port, String logFilePath) {
        this.port = port;
        this.logFilePath = logFilePath;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Socket created");
        while (true) {
            System.out.println("Waiting for connection...");
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New connection");
                new Worker(socket, logFilePath).start();
                System.out.println("Worker started");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        String filePath = "Networking/Exercise4/logs.txt";
        Server server = new Server(7391, filePath);
        server.start();
    }
}
