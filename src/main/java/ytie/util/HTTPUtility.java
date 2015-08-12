package ytie.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Jacob on 4/7/2015.
 */
public class HTTPUtility {
    public static String queryStringFromMap(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder encodedData = new StringBuilder();
        Iterator<Map.Entry<String, String>> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry<String, String> entry = mapIterator.next();
            if (mapIterator.hasNext()) {
                encodedData.append(String.format("%s&", urlEncodeEntry(entry)));
            } else {
                encodedData.append(urlEncodeEntry(entry));
            }
        }
        return encodedData.toString();
    }

    public static Map<String, String> queryStringToMap(String query) throws UnsupportedEncodingException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] encodedPairs = query.split("&");
        for (String pair : encodedPairs) {
            String[] splitPair = pair.split("=", -1);
            if (splitPair.length != 2) {
                throw new RuntimeException("query string not properly formatted");
            }

            String encodedKey = splitPair[0];
            String encodedValue = splitPair[1];
            hashMap.put(URLDecoder.decode(encodedKey, "utf-8"), URLDecoder.decode(encodedValue, "utf-8"));
        }
        return hashMap;
    }

    public static Map<String, String> queryStringToMap(String query, Boolean decode) throws UnsupportedEncodingException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] encodedPairs = query.split("&");
        for (String pair : encodedPairs) {
            String[] splitPair = pair.split("=", -1);
            if (splitPair.length != 2) {
                throw new RuntimeException("query string not properly formatted");
            }

            String encodedKey = splitPair[0];
            String encodedValue = splitPair[1];
            if (decode) {
                hashMap.put(URLDecoder.decode(encodedKey, "utf-8"), URLDecoder.decode(encodedValue, "utf-8"));
            } else {
                hashMap.put(encodedKey, encodedValue);
            }
        }
        return hashMap;
    }

    public static String urlEncodeEntry(Map.Entry<String, String> entry) throws UnsupportedEncodingException {
        String encodedEntry = String.format("%s=%s",
                URLEncoder.encode(entry.getKey(), "utf-8"),
                URLEncoder.encode(entry.getValue(), "utf-8"));
        return encodedEntry;
    }

    public static String downloadPageSource(String stringURL) throws IOException {
        URL url;
        HttpURLConnection conn;
        StringBuilder source = new StringBuilder();

        try {
            url = new URL(stringURL);
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException ex) {
            throw ex;
        }

        try {
            String line;
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = in.readLine()) != null)
                source.append(line);
        } catch (IOException ex) {
            throw ex;
        } finally {
            conn.disconnect();
        }

        return source.toString();
    }
}
