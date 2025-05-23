package Networking.Exercise3;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
    Socket clientSocket;
    String logFile;

    static int totalSum = 0;
    static Lock lock = new ReentrantLock();

    public Worker(Socket clientSocket, String logFile) {
        this.clientSocket = clientSocket;
        this.logFile = logFile;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedWriter fileWriter = null;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            fileWriter = new BufferedWriter(new FileWriter(logFile, true));

            String line = reader.readLine();

            if (line.equals("HANDSHAKE")) {
                writer.write("Logged in " + clientSocket.getRemoteSocketAddress() + "\n");
                writer.flush();

                int localSum = 0;
                while (!(line = reader.readLine()).equals("STOP")) {
                    int number = Integer.parseInt(line);
                    localSum += number;
                    fileWriter.write((number + " " + LocalDateTime.now() + " " + clientSocket.getRemoteSocketAddress() + "\n").toString());
                    fileWriter.flush();
                }

                lock.lock();
                totalSum += localSum;
                writer.write(totalSum + "\n");
                lock.unlock();

                writer.write("Logged out\n");
                writer.flush();

                writer.close();
                reader.close();
                fileWriter.close();
                clientSocket.close();

            } else {
                writer.write("Could not connect to Server\n");
                writer.flush();

                writer.close();
                reader.close();
                fileWriter.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
