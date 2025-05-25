package Networking.Exercise6;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    int port;
    String address;
    String filePath;
    String emailTo;
    String emailFrom;
    String emailCC;

    public Client(int port, String address, String filePath, String emailTo, String emailFrom, String emailCC) {
        this.port = port;
        this.address = address;
        this.filePath = filePath;
        this.emailTo = emailTo;
        this.emailFrom = emailFrom;
        this.emailCC = emailCC;
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
            writer.write("MAIL TO: " + emailTo);
            writer.newLine();
            writer.flush();

            line = reader.readLine();
            System.out.println(line);
            writer.write("MAIL FROM: " + emailFrom);
            writer.newLine();
            writer.flush();

            line = reader.readLine();
            System.out.println(line);
            writer.write("MAIL_CC: " + emailCC);
            writer.newLine();
            writer.flush();

            line = reader.readLine();
            System.out.println(line);
            line = fileReader.readLine();
            while (line != null) {
                writer.write(line);
                writer.newLine();
                writer.flush();
                line = fileReader.readLine();
            }
            writer.newLine();
            writer.flush();

            line = reader.readLine();
            System.out.println(line);

            writer.write("EXIT");
            writer.newLine();
            writer.flush();
            System.out.println("Exit message sent");
            reader.close();
            writer.close();
            fileReader.close();
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Client client = new Client(8765, "localhost", "Networking/Exercise6/email.txt", "user@gmail.com", "user@yahoo.com", "user@hotmail.com");
        client.start();
    }
}
