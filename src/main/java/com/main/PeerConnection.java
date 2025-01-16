package com.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PeerConnection {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String infoHash;
    private String peerId;

    public PeerConnection (Peer peer, String infoHash, String peerId) throws IOException {
        this.peerId = peerId;
        this.infoHash = infoHash;
        socket = new Socket();
        socket.connect(new InetSocketAddress(peer.getIp(), peer.getPort()), 5000);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void performHandshake() throws IOException {
        byte[] handshake = new byte[68];
        handshake[0] = 19;
        System.arraycopy("BitTorrent Protocol".getBytes(), 0, handshake, 1, 19);
        handshake[25] = (byte) 0x19;
        System.arraycopy(infoHash.getBytes(), 0, handshake, 28, 20);
        System.arraycopy(peerId.getBytes(), 0, handshake, 48, 20);

        out.write(handshake);
        out.flush();

        byte[] response = new byte[68];
        in.readFully(response);
    }

}
