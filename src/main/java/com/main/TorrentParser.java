package com.main;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//magnet:?xt=urn:btih:FA9773B96054338D1F1C10F2A4D2D9BD5A578B92&dn=Interstellar+%282014%29+%5B2160p%5D+%5BYTS.MX%5D&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969%2Fannounce&tr=udp%3A%2F%2F9.rarbg.to%3A2710%2Fannounce&tr=udp%3A%2F%2Fp4p.arenabg.ch%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.cyberia.is%3A6969%2Fannounce&tr=http%3A%2F%2Fp4p.arenabg.com%3A1337%2Fannounce&tr=udp%3A%2F%2Ftracker.internetwarriors.net%3A1337%2Fannounce
public class TorrentParser {

	public Map<String, String> parseMagnetLink(String magnetURL) throws URISyntaxException, UnsupportedEncodingException {
		String query = magnetURL.substring(8);
		Map<String, String> params = new HashMap<>();

		int keyIdx = 0;
		for(String pair : query.split("&")){
			int idx = pair.indexOf('=');
			String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
			String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");

			if(params.containsKey(key)){
				key = key.concat(".").concat(String.valueOf(keyIdx++));
			}
			params.put(key, value);
		}

		return params;
	}

	public Map<String, String> parseTorrentFile(String torrFileLocation) throws IOException {
		File torrFile = new File(torrFileLocation);
		byte[] torrFileData = Files.readAllBytes(torrFile.toPath());
		return null;
	}
	
}