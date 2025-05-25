package Networking.Exercise7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    int port;
    String fileOutput;

    public Server(int port, String fileName) {
        this.port = port;
        this.fileOutput = fileName;
    }

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Worker started...");
                new Worker(clientSocket, fileOutput).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8888, "Networking/Exercise7/data.csv");
        server.start();
    }
}
