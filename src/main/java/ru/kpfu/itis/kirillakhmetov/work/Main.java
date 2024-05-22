package ru.kpfu.itis.kirillakhmetov.work;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        try (FileWriter fwLog = new FileWriter("v2.log");
             FileWriter fwText = new FileWriter("v2.txt");
             PrintWriter pwLog = new PrintWriter(fwLog);
             PrintWriter pwText = new PrintWriter(fwText)) {

            File folder = new File("v2");
            File[] files = folder.listFiles();
            List<FileInfo> filesInfo = new ArrayList<>();

//            int countThreads = Runtime.getRuntime().availableProcessors();
            ExecutorService executorService = Executors.newFixedThreadPool(files.length);

            for (File file : files) {
                executorService.execute(new Work(file, pwLog, filesInfo));
            }

            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.DAYS);

            filesInfo.sort(Comparator.comparingInt(f -> f.p));

            for (FileInfo content : filesInfo) {
                pwText.print(content.s);
            }
        }
    }
}