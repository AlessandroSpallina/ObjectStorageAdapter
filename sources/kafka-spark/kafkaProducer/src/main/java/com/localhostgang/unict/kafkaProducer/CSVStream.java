package com.localhostgang.unict.kafkaProducer;

import com.localhostgang.unict.kafkaProducer.KProducer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVStream {
    private String csvFile = "/home/manlio/Scrivania/metrics.csv";
    private String line = "";
    private String csvSplitBy = ",";
    private BufferedReader br = new BufferedReader(new FileReader(csvFile));

    private KProducer kafkaProducer = new KProducer();

    public CSVStream() throws FileNotFoundException, IOException {
        try{
            // la variabile iteration = 0 serve all'interno del while per evitare di
            // inviare gli header del csv file al broker
            int iteration = 0;
            while((line = br.readLine()) != null) {
                if(iteration == 0) {
                    iteration++;
                    continue; // il continue ci farà tornare ad inizio while
                }
                String[] metric = line.split(csvSplitBy);

                String metrictopush = metric[0]+" "+metric[1]+" "+metric[2]+" "+metric[3];
                // System.out.println("info:"+metric[0]+" "+metric[1]+" "+metric[2]+" "+metric[3]+" ");
                // System.out.println(metrictopush);
                kafkaProducer.runProducer(metrictopush);
            }

        } catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
