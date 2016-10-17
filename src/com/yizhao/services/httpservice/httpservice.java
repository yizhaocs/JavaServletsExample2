package com.yizhao.services.httpservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by yzhao on 10/17/16.
 */
public class httpService {
    public static void main(String[] args) throws Exception{
        System.out.println(httpGet("http://www.google.com"));
    }

    public static String httpGet(String url) throws Exception{
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setDoOutput(true);
        connection.setReadTimeout(120000);

        InputStream response = connection.getInputStream();

        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            for (String line; (line = reader.readLine()) != null;) {
                sb.append(line).append("\n");
            }
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException ioe) {}
        }

        return sb.toString();
    }
}
