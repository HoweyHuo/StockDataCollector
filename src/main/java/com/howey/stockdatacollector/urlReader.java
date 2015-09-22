package com.howey.stockdatacollector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2015/7/13.
 */
public class urlReader {
    public String readUrl(String urlString, String charset) throws Exception {
        BufferedReader reader = null;
        try {
            URLConnection connection = new URL(urlString).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}
