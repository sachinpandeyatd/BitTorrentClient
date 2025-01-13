package com.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class TorrentParser {

	public void parseMaganetLink() {
		System.out.print("Please enter your maganet link - ");
		Scanner scanner = new Scanner(System.in);
		String maganetURL = scanner.next();
		scanner.close();
	}

	public void parseTorrentFile() throws IOException {
		System.out.println("Please enter your torrent file location");
		Scanner scanner = new Scanner(System.in);
		String torrFileLocation = scanner.next();
		scanner.close();
		
		File torrFile = new File(torrFileLocation);
		byte[] torrFileData = Files.readAllBytes(torrFile.toPath());
	}
	
}