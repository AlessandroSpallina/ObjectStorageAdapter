package com.localhostgang.unict.kafkaProducer;

import com.localhostgang.unict.kafkaProducer.KProducer;

import java.io.*;

public class CSVStream {
    private String csvFile = "/home/manlio/Scrivania/metrics.csv";
    private String line = "";
    private String csvSplitBy = ",";
    private BufferedReader br = new BufferedReader(new FileReader(csvFile));


    File file = new File("/home/manlio/Scrivania/metrics.csv");
    private long firsttimestamp = 0; //file.lastModified();



    private boolean isModified = false;

    private KProducer kafkaProducer = new KProducer();

    public CSVStream() throws FileNotFoundException, IOException {
        try{
            // la variabile iteration = 0 serve all'interno del while per evitare di
            // inviare gli header del csv file al broker
            while(true) {
                if(firsttimestamp == 0 || firsttimestamp != file.lastModified()) {
                    firsttimestamp = file.lastModified();
                    int iteration = 0;
                    while ((line = br.readLine()) != null) {
                        if (iteration == 0) {
                            iteration++;
                            continue; // il continue ci farà tornare ad inizio while
                        }
                        String[] metric = line.split(csvSplitBy);
                        String metrictopush = metric[0] + " " + metric[1] + " " + metric[2] + " " + metric[3];
                        kafkaProducer.runProducer(metrictopush);
                    }
                } else {
                    Thread.sleep(120000);
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
