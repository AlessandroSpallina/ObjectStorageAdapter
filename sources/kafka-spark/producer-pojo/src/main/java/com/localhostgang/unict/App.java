package com.localhostgang.unict;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            CSVStream cs = new CSVStream();
            cs.streamFeed();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



}
