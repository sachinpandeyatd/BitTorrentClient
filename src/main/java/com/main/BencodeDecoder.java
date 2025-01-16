package com.main;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class BencodeDecoder {
    private int position;
    private byte[] data;

    public Object decode(byte[] responseBytes) throws IOException {
        this.position = 0;
        this.data = responseBytes;

        try {
            return decode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Object decode() throws IOException {
        if(position > data.length){
            throw new IOException("Unexpected end of data");
        }

        char c = (char) data[position];

        switch (c){
            case 'd' :
                return decodeDictionary();
            case 'l' :
                return decodeList();
            case 'i':
                return decodeInteger();
            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
                return decodeString();
            default:
                throw new IOException("Invalid bencode format at position " + position);
        }
    }

    private byte[] decodeString() throws IOException {
        int start = position;
        while (position < data.length && data[position] != ':') {
            position++;
        }

        if(position >= data.length){
            throw new IOException("Unexpected end of string length");
        }

        String lengthStr = new String(data, start, position - start, StandardCharsets.UTF_8);
        int length = Integer.parseInt(lengthStr);
        position++;

        if(position + length > data.length) throw new IOException("String length exceeds data bounds");

        byte[] result = Arrays.copyOfRange(data, position, position + length);
        position += length;

        System.out.println("hdsdhsjd   " + Arrays.toString(result));
        return result;
    }

    private Object decodeInteger() {
        return null;
    }

    private Object decodeList() {
        return null;
    }

    private Map<String, Object> decodeDictionary() throws IOException {
        System.out.println("jkdskdjskjk");
        position += 1; //skip first char i.e. 'd' in this case
        Map<String, Object> decodedData = new HashMap<>();

        while(position < data.length && data[position] != 'e'){
            String key = new String(decodeString(), StandardCharsets.UTF_8);
            Object value = decode();
            decodedData.put(key, value);
        }

        System.out.println("jkdskjdsk   " + decodedData);
        if(position > data.length) throw new IOException("Unexpected end of dictionary");

        position += 1;
        return decodedData;
    }
}
