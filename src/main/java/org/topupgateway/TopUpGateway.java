package org.topupgateway;

import org.topupgateway.annotation.Property;
import org.topupgateway.thread.PrimaryThread;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TopUpGateway {
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        FileReader fileReader = new FileReader(System.getProperty("user.dir") + "/src/main/resources/config.properties");
        Properties properties = new Properties();
        properties.load(fileReader);

        int numberOfPrimaryThread = Integer.parseInt(properties.getProperty("noOfMainThread"));
        int numberOfSecondaryThread = Integer.parseInt(properties.getProperty("noOfSubThread"));

        while (true){

            ExecutorService executorService = Executors.newFixedThreadPool(numberOfPrimaryThread);
            for (int i = 0; i < numberOfPrimaryThread; i++){
                Runnable primaryThread = new PrimaryThread(numberOfSecondaryThread, i);
                executorService.execute(primaryThread);
            }
            Thread.sleep(1000);
            executorService.shutdown();
        }
    }
}