package com.main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PeerHandshake {

    public byte[] extractPeerData(String trackerResponse){

        return null;
    }


    public byte[] extractPeersFromResponse(String response) throws IOException {
        // Decode the bencoded response
        byte[] responseBytes = response.getBytes(StandardCharsets.ISO_8859_1);
        BencodeDecoder bcdecoder = new BencodeDecoder();
        Object decoded = bcdecoder.decode(responseBytes);

        if (!(decoded instanceof Map)) {
            throw new IOException("Invalid tracker response format");
        }

        Map<String, Object> responseMap = (Map<String, Object>) decoded;

        Object peersObj = responseMap.get("peers");

        if(peersObj == null){
            throw new IOException("No peer in tracker response");
        }

        byte[] peersData;

        if(peersObj instanceof ByteBuffer){
            ByteBuffer byteBuffer = (ByteBuffer) peersObj;
            peersData = new byte[byteBuffer.remaining()];
            byteBuffer.get(peersData);
        }else if(peersObj instanceof byte[]) {
            peersData = (byte[]) peersObj;
        }else {
            throw new IOException("Unexpected peers data format");
        }

        System.out.println("peersData --  " + Arrays.toString(peersData));

        return peersData;
    }

    public List<Peer> parsePeersData(byte[] peersData){
        List<Peer> peers = new ArrayList<>();

        for(int i = 0; i < peersData.length; i += 6){
            if(i + 6 > peersData.length) break;

            String peerIP = String.format(
                    "%d.%d.%d.%d",
                    peersData[i] & 0xFF,
                    peersData[i + 1] & 0xFF,
                    peersData[i + 2] & 0xFF,
                    peersData[i + 3] & 0xFF
            );

            int peerPort = ((peersData[i + 4] & 0xFF) << 8) | (peersData[i + 5] & 0xFF);
            System.out.println("peerIP and port ---- " + peerIP + ":" + peerPort);

            peers.add(new Peer(peerIP, peerPort));
        }

        System.out.println("Peers data list --- " + peers);
        return peers;
    }
}
