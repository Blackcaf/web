package ru.rmntim;

import com.fastcgi.FCGIInterface;

public class Main {
    public static void main(String[] args) {
        var server = new WebServer();
        server.start();
    }
}