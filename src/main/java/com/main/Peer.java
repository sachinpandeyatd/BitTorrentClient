package com.main;

public class Peer  {
    private String ip;
    private int port;

    public Peer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    // Getters
    public String getIp() { return ip; }
    public int getPort() { return port; }

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}
