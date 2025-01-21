package com.main;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BencodeEncoder {
    public static String encode(Object obj) {
        if (obj instanceof String) {
            return encodeString((String) obj);
        } else if (obj instanceof byte[]) {
            return encodeBytes((byte[]) obj);
        } else if (obj instanceof Number) {
            return encodeNumber((Number) obj);
        } else if (obj instanceof List) {
            return encodeList((List<?>) obj);
        } else if (obj instanceof Map) {
            return encodeMap((Map<String, ?>) obj);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
        }
    }

    private static String encodeMap(Map<String,?> obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("l");

        // Sort keys to maintain canonical form
        TreeMap<String, ?> sortedMap = new TreeMap<>(obj);

        for(Map.Entry<String, ?> entry : sortedMap.entrySet()){
            sb.append(encode(entry.getKey()));
            sb.append(encode(entry.getValue()));
        }

        sb.append("e");
        return sb.toString();
    }

    private static String encodeList(List<?> objList) {
        StringBuilder sb = new StringBuilder();
        sb.append("l");
        for(Object obj : objList){
            sb.append(encode(obj));
        }
        sb.append("e");
        return sb.toString();
    }

    private static String encodeNumber(Number obj) {
        return "i" + obj.toString() + "e";
    }

    private static String encodeBytes(byte[] obj) {
        return obj.length + ":" + new String(obj, StandardCharsets.ISO_8859_1);
    }

    private static String encodeString(String obj) {
        return obj.length() + ":" + obj;
    }
}
