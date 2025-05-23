package Networking;

import java.io.IOException;
import java.net.*;

public class UDPClientSide extends Thread {
    private String serverName;
    private int serverPort;

    private DatagramSocket socket;
    private InetAddress address;
    private String message;
    private byte[] buffer;

    public UDPClientSide(String serverName, int serverPort, String message) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.message = message;

        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName("194.149.135.49");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, serverPort);

        try {
            socket.send(packet);

            byte[] newBuffer = new byte[256];
            DatagramPacket receivedPacket = new DatagramPacket(newBuffer, newBuffer.length, address, serverPort);

            socket.receive(receivedPacket);
            System.out.println(new String(receivedPacket.getData(), 0, receivedPacket.getLength()));
            System.out.println(receivedPacket.getAddress().getHostAddress());
            System.out.println(receivedPacket.getPort());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UDPClientSide client = new UDPClientSide("localhost", 9753, "231026");
        client.start();
    }
}
