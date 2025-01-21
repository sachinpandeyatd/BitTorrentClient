package com.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeerConnection {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final String infoHash;
    private final String peerId;

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

        System.out.println("handshake array ---- " + Arrays.toString(handshake));
        out.write(handshake);
        out.flush();

        byte[] response = new byte[68];
        in.readFully(response);
    }

    public void enableMetaDataExtension() throws IOException {
        String extHandshake = createExtensionHandshake();

        byte[] message = createExtensionMessage(0, extHandshake.getBytes());
        out.write(message);
        out.flush();
    }

    private String createExtensionHandshake() {
        Map<String, Object> handshake = new HashMap<>();
        Map<String, Integer> m = new HashMap<>();
        m.put("ut_metadata", 1);

        handshake.put("m", m);
        handshake.put("v", "MyTorrentClient");
        handshake.put("metadata_size", 0);


        return BencodeEncoder.encode(handshake);
    }

    private byte[] createExtensionMessage(int extId, byte[] payload) {
        int length = 2 + payload.length;
        ByteBuffer buf = ByteBuffer.allocate(4 + length);
        buf.putInt(length);
        buf.put((byte) 20);
        buf.put((byte) extId);
        buf.put(payload);
        return buf.array();
    }

    public long requestMetadata() throws IOException {
        // First, request metadata size
        sendMetadataRequest(0); // Request piece 0

        // Read peer's response
        while(true){
            // Read message length
            byte[] lengthBytes = new byte[4];
            in.readFully(lengthBytes);
            int length = ByteBuffer.wrap(lengthBytes).getInt();

            // Read message type
            int messageType = in.read();
            if(messageType == 20){ // Extension message
                int extId = in.read();
                byte[] payload = new byte[length - 2];
                in.readFully(payload);

                // Parse metadata piece
                BencodeDecoder decoder = new BencodeDecoder();
                Map<String, Object> piece = (Map<String, Object>) decoder.decode(payload);
                // Extract metadata size and piece data
                int metadataSize = ((Long) piece.get("total_size")).intValue();
                byte[] pieceData = (byte[]) piece.get("piece");

                // Parse metadata to get file info
                Map<String, Object> info = (Map<String, Object>) decoder.decode(pieceData);

                // Extract total size from info dictionary
                if (info.containsKey("length")) {
                    // Single file torrent
                    return (Long) info.get("length");
                } else if (info.containsKey("files")) {
                    // Multiple files torrent
                    List<Map<String, Object>> files = (List<Map<String, Object>>) info.get("files");
                    long totalSize = 0;
                    for (Map<String, Object> file : files) {
                        totalSize += (Long) file.get("length");
                    }
                    return totalSize;
                }
            }
        }
    }

    private void sendMetadataRequest(int piece) throws IOException {
        Map<String, Object> request = new HashMap<>();
        request.put("msg_type", 0);
        request.put("piece", piece);

        String bencoded = BencodeEncoder.encode(request);
        byte[] message = createExtensionMessage(1, bencoded.getBytes(StandardCharsets.ISO_8859_1));
        out.write(message);
        out.flush();
    }

    public void close() throws SocketException {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            throw new SocketException("Socket couldn't be close");
        }
    }
}
