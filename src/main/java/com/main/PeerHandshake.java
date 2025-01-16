package com.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PeerHandshake {

    public byte[] extractPeerData(String trackerResponse){

        return null;
    }


    public byte[] extractPeersFromResponse(String response) throws IOException {
        // Decode the bencoded response
        byte[] responseBytes = response.getBytes(StandardCharsets.ISO_8859_1);
        BencodeDecoder bcdecoder = new BencodeDecoder();
        Map<String, Object> decoded = (Map<String, Object>) bcdecoder.decode(responseBytes);

        System.out.println("kdskjdsjkdskj " + decoded);

        // Get the peers bytes
        return null; //(byte[]) decoded.get("peers");
    }
}
