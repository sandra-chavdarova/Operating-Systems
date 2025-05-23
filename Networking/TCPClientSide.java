package Networking;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class TCPClientSide extends Thread {
    private final int serverPort;

    public TCPClientSide() {
        this.serverPort = 9753;
    }

    @Override
    public void run() {
        Socket socket;
        try {
            socket = new Socket(InetAddress.getByName("194.149.135.49"), serverPort);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connecting to server...");
            String message = "hello:231026\n";
            bw.write(message);
            bw.flush();

            String loginReturnMessage = br.readLine();
            if (loginReturnMessage == null || !loginReturnMessage.contains("Succesfully")) {
                System.out.println("Login was unsuccessful. Closing connection...");
                socket.close();
                return;
            }
            System.out.println();
            System.out.println("Login successful!");
            System.out.println(loginReturnMessage);
            bw.write("hello:231026\n");
            bw.flush();

            String helloReturnMessage = br.readLine();
            if (helloReturnMessage == null || !helloReturnMessage.contains("welcome")) {
                System.out.println("Login was unsuccessful. Closing connection...");
                socket.close();
                return;
            }

            System.out.println();
            System.out.println("Hello successful!");
            System.out.println(helloReturnMessage);

            System.out.println();
            System.out.println("Index: Message");

            Thread receiveThread = new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = br.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closing...");
                }
            });

            receiveThread.start();

            String messageInfo;
            while ((messageInfo = input.readLine()) != null) {
                if (!messageInfo.contains(":")) {
                    System.out.println("Invalid format. Use \"Index: Message\" format.");
                    continue;
                }
                bw.write(messageInfo + "\n");
                bw.flush();
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TCPClientSide client = new TCPClientSide();
        client.start();
    }
}