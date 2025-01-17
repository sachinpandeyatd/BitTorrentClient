package com.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		System.out.println("What are you having - \n1) Magnet Link \n2) Torrent File \nJust enter the number");
		Scanner scanner = new Scanner(System.in);
		int inputType = scanner.nextInt();
		scanner.nextLine();

		TorrentParser torrentParser = new TorrentParser();
		Map<String, String> params = new HashMap<>();
		switch (inputType) {
			case 1:
			System.out.print("Please enter your magnet link - ");
			String magnetURL = scanner.nextLine();
			scanner.close();

            try {
                params = torrentParser.parseMagnetLink(magnetURL);
				System.out.println(params);
            } catch (URISyntaxException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            break;

		case 2:
			System.out.println("Please enter your torrent file location");
			String torrFileLocation = scanner.nextLine();
			scanner.close();
			try {
				params = torrentParser.parseTorrentFile(torrFileLocation);
			} catch (IOException e) {
				System.out.println("An exception occurred - " + e.getMessage());
			}
			break;
		}

		TrackerCommunication trackerCommunication = new TrackerCommunication();
        try {
            String trackerResponse = trackerCommunication.connectToTrackers(params);
			System.out.println("Tracker Response ----  " + trackerResponse);
			if(trackerResponse != null){
				ExtractPeerData extractPeerData = new ExtractPeerData();
				byte[] peerData = extractPeerData.extractPeersFromResponse(trackerResponse);

				if(peerData != null){
					List<Peer> peers = extractPeerData.parsePeersData(peerData);

					if(peers != null){

					}
				}
			}
        } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
