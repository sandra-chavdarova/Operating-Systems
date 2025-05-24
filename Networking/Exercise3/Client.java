package Networking.Exercise3;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    int port;
    String address;

    public Client(int port, String address) {
        this.port = port;
        this.address = address;
    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            socket = new Socket(InetAddress.getByName(address), port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write("GET /hello HTTP/1.1");
            writer.newLine();
            writer.write("Host: developer.mozilla.org");
            writer.newLine();
            writer.write("User agent: OS Client");
            writer.newLine();
            writer.newLine();
            writer.flush();

            String line = reader.readLine();
            ;
            while (line != null) {
                System.out.println("Client received: " + line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(8080, "localhost");
        client.start();
    }
}
