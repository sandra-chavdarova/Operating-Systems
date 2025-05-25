package Networking.Exercise7;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;

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

            String line = reader.readLine();
            System.out.println(line);
            writer.write("HELLO " + socket.getPort());
            writer.newLine();
            writer.flush();

            line = reader.readLine();
            System.out.println(line);
            if (line.contains("DATA")) {
                while ((line = fileReader.readLine()) != null && !line.isEmpty()) {
                    writer.write(LocalDateTime.now() + ", " + line);
                    writer.newLine();
                    writer.flush();
                }
            } else {
                writer.write("I am not permitted to send data");
                writer.newLine();
                writer.flush();

                writer.close();
                reader.close();
                fileReader.close();
                socket.close();
            }

            line = reader.readLine();
            System.out.println(line);
            writer.write("QUIT");
            writer.newLine();
            writer.flush();

            writer.close();
            reader.close();
            fileReader.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(8888, "localhost", "Networking/Exercise7/covidUpdate.csv");
        client.start();
    }
}
