package Networking.Exercise6;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread {
    Socket socket;
    String fileName;
    String emailTo;
    String emailFrom;
    String emailCC;

    int counter = 0;
    static Lock lock = new ReentrantLock();

    public Worker(Socket socket, String fileName) {
        this.socket = socket;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedWriter fileWriter = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            fileWriter = new BufferedWriter(new FileWriter(fileName, true));

            String line = "START " + socket.getRemoteSocketAddress();
            writer.write(line);
            writer.newLine();
            writer.flush();
            fileWriter.write(line);
            fileWriter.newLine();
            fileWriter.flush();

            line = reader.readLine();
            fileWriter.write(line);
            fileWriter.newLine();
            fileWriter.flush();
            emailTo = line.split(": ")[1];
            try {
                if (emailTo.contains("@")) {
                    writer.write("TNX");
                    writer.newLine();
                    writer.flush();
                } else {
                    throw new Exception("Invalid email address");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            line = reader.readLine();
            fileWriter.write(line);
            fileWriter.newLine();
            fileWriter.flush();
            emailFrom = line.split(": ")[1];
            try {
                if (emailFrom.contains("@")) {
                    writer.write("200 OK");
                    writer.newLine();
                    writer.flush();
                } else {
                    throw new Exception("Invalid email address");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            line = reader.readLine();
            fileWriter.write(line);
            fileWriter.newLine();
            fileWriter.flush();
            emailCC = line.split(": ")[1];
            writer.write("RECEIVERS: " + emailTo + " " + emailCC);
            writer.newLine();
            writer.flush();

            line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                fileWriter.write(line);
                fileWriter.newLine();
                fileWriter.flush();
                lock.lock();
                counter++;
                lock.unlock();
                line = reader.readLine();
            }

            writer.write("RECEIVED " + counter + " lines");
            writer.newLine();
            writer.flush();

            line = reader.readLine();
            if (line.equals("EXIT")) {
                fileWriter.write(line);
                fileWriter.newLine();
                fileWriter.flush();
                System.out.println("EXIT");
            }
            reader.close();
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
