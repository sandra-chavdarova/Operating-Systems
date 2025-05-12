package DockerLabs.Exercise3.Sensor;

import java.io.*;
import java.util.*;

public class TemperatureSensor {
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        File file = new File("/data/temperature.txt");

        while (true) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                for (int i = 0; i < 5; i++) {
                    int temp = 5 + rand.nextInt(46);
                    writer.write(temp + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(30000);
	}
    }
}
