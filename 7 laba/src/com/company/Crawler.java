package com.company;

import java.io.*;
import java.net.*;
import java.util.LinkedList;


public class Crawler {

    final static int ADepth = 0;
    private LinkedList<URLDepthPair> Processed = new LinkedList<URLDepthPair>();
    private LinkedList<URLDepthPair> NotProcessed = new LinkedList<URLDepthPair>();

    private int Depth;
    private String StartHost;
    // у префикса нету слэша для поддержки https
    private String Prefix = "http";

    public Crawler(String host, int depth) {
        StartHost = host;
        Depth = depth;
        NotProcessed.add(new URLDepthPair(StartHost, Depth));
    }

    public void Scan() throws IOException {

        while (NotProcessed.size() > 0) {
            Process(NotProcessed.removeFirst());
        }
    }


    public void Process(URLDepthPair pair) throws IOException {
        // устанавливает соединение и следует перенаправлению
        URL url = new URL(pair.getURL());
        URLConnection connection = url.openConnection();
        String redirect = connection.getHeaderField("Location");
        if (redirect != null) {
            connection = new URL(redirect).openConnection();
        }
        Processed.add(pair);
        if (pair.getDepth() == 0) return;

        // читает ссылки
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String input;
        while ((input = reader.readLine()) != null) {
            while (input.contains("a href=\"" + Prefix)) {
                input = input.substring(input.indexOf("a href=\"" + Prefix) + 8);
                String link = input.substring(0, input.indexOf('\"'));
                if (link.contains(" "))
                    link = link.replace(" ", "%20");
                // избегает многократное посещение одной и той же ссылки
                if (NotProcessed.contains(new URLDepthPair(link, ADepth)) ||
                        Processed.contains(new URLDepthPair(link, ADepth))) continue;
                NotProcessed.add(new URLDepthPair(link, pair.getDepth() - 1));
            }
        }
        reader.close();

    }

    public void getSites() {
        // printing the links
        for (var elem : Processed)
            System.out.println(elem.getURL());
        System.out.println("Links visited: " + Processed.size());
    }
}