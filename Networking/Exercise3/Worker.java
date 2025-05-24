package Networking.Exercise3;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Worker extends Thread {
    Socket clientSocket;
    String fileName;

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

            WebRequest request = WebRequest.parse(reader);
            fileWriter.write(request.method + " " + request.url + " " + request.headers.get("User agent"));
            fileWriter.newLine();
            writer.flush();
            fileWriter.close();

            writer.write("HTTP 200 OK");
            writer.newLine();
            writer.write("Your request was successful");
            writer.newLine();
            writer.flush();

            writer.close();
            reader.close();
            clientSocket.close();
            System.out.println("Logging out...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class WebRequest {
    String method;
    String url;
    String version;

    Map<String, String> headers;

    public WebRequest(String method, String url, String version, Map<String, String> headers) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.headers = headers;
    }

    static WebRequest parse(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        String[] parts = line.split(" ");
        String method = parts[0];
        String url = parts[1];
        String version = parts[2];

        Map<String, String> headers = new HashMap<>();
        line = reader.readLine();
        while (!line.isEmpty()) {
            parts = line.split(": ");
            headers.put(parts[0], parts[1]);
            line = reader.readLine();
        }

        return new WebRequest(method, url, version, headers);
    }
}
