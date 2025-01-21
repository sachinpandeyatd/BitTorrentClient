package com.main;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class TrackerCommunication {

    public List<TorrentData> connectToTrackers(Map<String, String> params) throws IOException, InterruptedException, NoSuchAlgorithmException {
        List<TorrentData> torrentDataList = new ArrayList<>();
        String response = null;
        MessageDigest md = MessageDigest.getInstance("SHA1");
        String infoHash = urlEncode(md.digest(params.get("xt").split(":")[2].getBytes()));
        List<String> trackerList = params.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("t"))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        HttpClient httpClient = HttpClient.newHttpClient();

        for(String tracker : trackerList){
            String trackerUriScheme = URI.create(tracker).getScheme();
            String peerId = generatePeerId();
            TorrentStats torrentStats = new TorrentStats();
            URI trackerURI = URI.create(torrentStats.createTrackerUrl(tracker, infoHash, peerId));

            if(trackerUriScheme.equals("http") || trackerUriScheme.equals("https")) {
                HttpRequest trackerRequest = HttpRequest.newBuilder().uri(trackerURI).GET().build();

                HttpResponse<String> trackerResponse = httpClient.send(trackerRequest, HttpResponse.BodyHandlers.ofString());
                response = trackerResponse.body();
            }

            TorrentData torrentData = new TorrentData(peerId, tracker, infoHash, response, torrentStats);
            torrentDataList.add(torrentData);
        }
        return torrentDataList;
    }

    private static String generatePeerId() {
        String prefix = "-MY0001-";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(prefix);

        // Generate 12 random digits
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    private String urlEncode(byte[] bs) {
        StringBuffer sb = new StringBuffer(bs.length * 3);
        for (byte b : bs) {
            int c = b & 0xFF;
            sb.append('%');
            if (c < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(c));
        }
        return sb.toString();
    }
}
