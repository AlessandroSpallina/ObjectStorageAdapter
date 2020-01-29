package com.localhostgang.unict;

import com.localhostgang.unict.common.Utils;

import java.io.*;
import java.util.Properties;

public class CSVStream {
    private final Properties prop = Utils.loadProperties("consumer.properties");

    private long firstTimestamp = 0;
    private String csvSplitBy = ",";
    private BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("consumer.filepath")));
    private File file = new File(prop.getProperty("consumer.filepath"));
    private int counter = 0;
    private KProducer kp = new KProducer();

    public CSVStream() throws FileNotFoundException {
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
                Thread.sleep(120000);
            }
        }
    }
}
