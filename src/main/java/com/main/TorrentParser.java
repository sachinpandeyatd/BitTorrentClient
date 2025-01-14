package com.main;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TorrentParser {

	public Map<String, String> parseMagnetLink(String magnetURL) throws URISyntaxException, UnsupportedEncodingException {
		URI uri = new URI(magnetURL);
		String query = uri.getRawQuery();
		Map<String, String> params = new HashMap<>();

		for(String pair : query.split("&")){
			int idx = pair.indexOf('=');
			String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
			String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
			params.put(key, value);
		}

		return params;
	}

	public void parseTorrentFile(String torrFileLocation) throws IOException {
		File torrFile = new File(torrFileLocation);
		byte[] torrFileData = Files.readAllBytes(torrFile.toPath());
	}
	
}