package com.main;

import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		System.out.println("What are you having - \n1) Maganet Link \n2) Torrent File \nJust enter the number");
		Scanner scanner = new Scanner(System.in);
		int inputType = scanner.nextInt();
		
		TorrentParser torrentParser = new TorrentParser();
		switch (inputType) {
		case 1:
			torrentParser.parseMaganetLink();
			break;

		case 2:
			torrentParser.parseTorrentFile();
			break;
		}
	}
}
