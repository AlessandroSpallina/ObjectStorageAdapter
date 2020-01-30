package com.localhostgang.unict;

import java.io.*;

public class CSVStream {
    private long firstTimestamp = 0;
    private String csvSplitBy = ",";
    private BufferedReader br = new BufferedReader(new FileReader(System.getProperty("METRICS_FILE", "/home/manlio/Scrivania/metrics.csv")));
    private File file = new File(System.getProperty("METRICS_FILE", "/home/manlio/Scrivania/metrics.csv"));
    private int counter = 0;
    private KProducer kp = new KProducer();

    public CSVStream() throws FileNotFoundException {
        // System.out.println(System.getProperty("METRICS_FILE", "/home/manlio/Scrivania/metrics.csv"));
    }

    public void streamFeed() throws IOException, InterruptedException {
        while(true) {
            if(firstTimestamp == 0 || firstTimestamp != file.lastModified()) {
                firstTimestamp = file.lastModified();
                String line;

                br.readLine(); //questo legge la prima riga, che non mi serve a un gazzo
                while((line = br.readLine()) != null) {
                    String[] metrics = line.split(csvSplitBy);
                    line = counter + " " + metrics[0] + " " + metrics[1] + " " + metrics[2] + " " + metrics[3];
                    kp.produceData(line);
                    counter ++;
                    // aggiunto dopo suggerimento Di Maria
                    Thread.sleep(3000);
                }
            } else {
                Thread.sleep(300000);
            }
        }
    }
}
