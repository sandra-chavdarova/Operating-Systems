package DockerLabs.Exercise2.Monitor;

import java.io.*;
import java.util.*;

public class TemperatureMonitor {
    public static void main(String[] args) throws InterruptedException {
        File input = new File("/data/temperature.txt");
        File output = new File("/data/temperaturelevel.txt");

        while (true) {
            try (BufferedReader reader = new BufferedReader(new FileReader(input));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
                String line;
                List<Integer> temps = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    try {
                        temps.add(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException ignored) {}
                }

                if (!temps.isEmpty()) {
                    double avg = temps.stream().mapToInt(i -> i).average().orElse(0);
                    String level = avg < 19 ? "Low" : avg <= 35 ? "Medium" : "High";
                    writer.write(level + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(60000);
	}
    }
}
