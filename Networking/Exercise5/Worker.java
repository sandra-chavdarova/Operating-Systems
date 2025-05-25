package Networking.Exercise5;


import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
    Socket clientSocket;
    String fileName;

    static Lock lock = new ReentrantLock();
    static int counter = 0;
    static List<String> words = new ArrayList<>();

    public Worker(Socket clientSocket, String fileName) {
        this.clientSocket = clientSocket;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedWriter fileWriter = null;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            fileWriter = new BufferedWriter(new FileWriter(fileName, true));

            String line = reader.readLine();
            if (line.equals("HANDSHAKE")) {
                writer.write("Logged in " + clientSocket.getRemoteSocketAddress());
                writer.newLine();
                writer.flush();

                line = reader.readLine();
                while (!line.equals("STOP")) {
                    if (words.contains(line.toLowerCase())) {
                        writer.write(line + " IMA");
                        writer.newLine();
                        writer.flush();
                    } else {
                        lock.lock();
                        counter++;
                        words.add(line.toLowerCase());
                        lock.unlock();
                        fileWriter.write(line + " " + LocalDateTime.now() + " " + clientSocket.getRemoteSocketAddress());
                        fileWriter.newLine();
                        fileWriter.flush();

                        writer.write(line + " NEMA");
                        writer.newLine();
                        writer.flush();
                    }
                    line = reader.readLine();
                }
                writer.write(String.valueOf(counter));
                writer.newLine();
                writer.write("LOGGED OUT");
                writer.newLine();
                writer.flush();

                reader.close();
                writer.close();
                fileWriter.close();
                clientSocket.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
