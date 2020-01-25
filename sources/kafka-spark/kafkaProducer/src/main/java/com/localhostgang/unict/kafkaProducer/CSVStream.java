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
            while((line = br.readLine()) != null) {

                String[] metric = line.split(csvSplitBy);

                kafkaProducer.runProducer(metric);

                // System.out.println("info:"+metric[0]+" "+metric[1]+" "+metric[2]+" "+metric[3]+" ");

            }

        }catch(IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
