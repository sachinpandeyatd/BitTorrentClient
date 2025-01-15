package com.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		System.out.println("What are you having - \n1) Maganet Link \n2) Torrent File \nJust enter the number");
		Scanner scanner = new Scanner(System.in);
		int inputType = scanner.nextInt();
		String magnetURL1 = scanner.nextLine();

		TorrentParser torrentParser = new TorrentParser();
		switch (inputType) {
			case 1:
			System.out.print("Please enter your magnet link - ");
			String magnetURL = scanner.nextLine();
			scanner.close();

            try {
                Map<String, String> params = torrentParser.parseMagnetLink(magnetURL);
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
				torrentParser.parseTorrentFile(torrFileLocation);
			} catch (IOException e) {
				System.out.println("An exception occured - " + e.getMessage());
			}
			break;
		}
	}
}
