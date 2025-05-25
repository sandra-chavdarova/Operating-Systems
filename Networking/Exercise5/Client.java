package Networking.Exercise5;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    int port;
    String address;
    String filePath;

    public Client(int port, String address, String filePath) {
        this.port = port;
        this.address = address;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedReader fileReader = null;
        Socket socket = null;

        try {
            socket = new Socket(InetAddress.getByName(address), port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            fileReader = new BufferedReader(new FileReader(filePath));

            writer.write("HANDSHAKE");
            writer.newLine();
            writer.flush();

//            Scanner scanner = new Scanner(System.in);
//            String line = scanner.nextLine();
            String line = fileReader.readLine();
            while (line != null) {
                writer.write(line);
                writer.newLine();
                writer.flush();

                line = reader.readLine();
                System.out.println(line);

//                line = scanner.nextLine();
                line = fileReader.readLine();
            }
            line = reader.readLine();
            System.out.println("Total words: " + line);

            writer.close();
            reader.close();
            fileReader.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(7391, "127.0.0.1", "Networking/Exercise5/words.txt");
        client.start();
    }
}
