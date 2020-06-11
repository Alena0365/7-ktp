package com.company;

import java.net.URLConnection;
import java.util.Objects;

public class URLDepthPair {

    private String Url;
    private int Depth;

    public URLDepthPair(String host, int depth) {
        Url = host;
        Depth = depth;
    }

    public String getURL() {
        return Url;
    }

    public int getDepth() {
        return Depth;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof URLDepthPair) {
            URLDepthPair o = (URLDepthPair)obj;
            return this.Url.equals(o.getURL());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}