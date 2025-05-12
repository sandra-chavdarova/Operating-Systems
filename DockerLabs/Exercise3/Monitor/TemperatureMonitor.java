package DockerLabs.Exercise3.Monitor;

import java.io.*;
import java.util.*;

public class TemperatureMonitor {
    public static void main(String[] args) {
        String inputFile = "/usr/src/shared/temperature.txt";
        String outputFile = "/usr/src/level/temperaturelevel.txt";

        int LOW_TEMPERATURE = Integer.parseInt(System.getenv("LOW_TEMPERATURE"));
        int MEDIUM_TEMPERATURE = Integer.parseInt(System.getenv("MEDIUM_TEMPERATURE"));
        int HIGH_TEMPERATURE = Integer.parseInt(System.getenv("HIGH_TEMPERATURE"));

        while (true) {
            try {
                List<Integer> temperatures = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        try {
                            temperatures.add(Integer.parseInt(line.trim()));
                        } catch (NumberFormatException ignored) {}
                    }
                }

                if (!temperatures.isEmpty()) {
                    double avg = temperatures.stream().mapToInt(Integer::intValue).average().orElse(0);
                    String level;

                    if (avg >= LOW_TEMPERATURE && avg < MEDIUM_TEMPERATURE) {
                        level = "Low";
                    } else if (avg >= MEDIUM_TEMPERATURE && avg < HIGH_TEMPERATURE) {
                        level = "Medium";
                    } else {
                        level = "High";
                    }

                    try (FileWriter writer = new FileWriter(outputFile, true)) {
                        writer.write(level + "\n");
                        writer.flush();
                        System.out.println("Wrote level: " + level);
                    }
                }

                Thread.sleep(60000);
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}