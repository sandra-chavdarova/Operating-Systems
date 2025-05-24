package Networking.Exercise4;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    String address;
    int port;
    String filePath;

    public Client(String address, int port, String filePath) {
        this.address = address;
        this.port = port;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(InetAddress.getByName(address), port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader fileReader = new BufferedReader(new FileReader(filePath));

            writer.write("HANDSHAKE\n");
            writer.flush();

            String line = reader.readLine();

            if (line.contains("Logged in")) {
                System.out.println(line);
                String number;
                while ((number = fileReader.readLine()) != null) {
                    writer.write(number + "\n");
                }
                writer.write("STOP\n");
                writer.flush();

                line = reader.readLine();
                System.out.println("Total sum: " + line);
                line = reader.readLine();
                System.out.println(line);

                fileReader.close();
                reader.close();
                writer.close();
                socket.close();
            } else {
                System.out.println(line);
                fileReader.close();
                reader.close();
                writer.close();
                socket.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        String filePath = "Networking/Exercise4/numbers.txt";
        Client client = new Client("127.0.0.1", 7391, filePath);
        client.start();
    }
}
