package Networking.Exercise7;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread {
    Socket socket;
    String fileName;

    public Worker(Socket socket, String fileName) {
        this.socket = socket;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        BufferedWriter fileWriter = null;
        BufferedReader fileReader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            fileWriter = new BufferedWriter(new FileWriter(fileName, true));
            fileReader = new BufferedReader(new FileReader("Networking/Exercise7/data.csv"));

            // for the header
            if (fileReader.readLine() == null) {
                fileWriter.write("date, No. new covid cases, No. hospitalized patients, No. recovered patients");
                fileWriter.newLine();
                fileWriter.flush();
            }

            writer.write("HELLO " + socket.getRemoteSocketAddress());
            writer.newLine();
            writer.flush();

            String line = reader.readLine();
            if (line != null) {
                writer.write("SEND DAILY DATA");
                writer.newLine();
                writer.flush();
            } else {
                // instead of Exception
                writer.write("Failed to receive message\n");
                writer.flush();
            }

            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(", ");
                if (parts.length != 4) {
                    writer.write("Failed to receive message\n");
                    writer.flush();
                } else {
                    fileWriter.write(line);
                    fileWriter.newLine();
                    fileWriter.flush();

                    writer.write("OK\n");
                    writer.flush();
                }
            }

            System.out.println("Logging out...");
            writer.close();
            reader.close();
            fileWriter.close();
            fileReader.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
