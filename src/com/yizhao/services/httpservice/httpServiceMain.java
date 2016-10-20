package com.yizhao.services.httpservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 */
public class HttpServiceMain {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
//        System.out.println(httpPost("http://echo.itcuties.com", "hi there!"));
        //System.out.println(httpGet("http://www.google.com"));

        System.out.println(sendGet("http://www.google.com/search?q=mkyong"));
    }
    // HTTP GET request
    private static String sendGet(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

   /* public static String httpGet(String url) throws Exception {
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setDoOutput(true);
        connection.setReadTimeout(120000);

        InputStream response = connection.getInputStream();

        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            for (String line; (line = reader.readLine()) != null; ) {
                sb.append(line).append("\n");
            }
        } finally {
            if (reader != null) try {
                reader.close();
            } catch (IOException ioe) {
            }
        }

        return sb.toString();
    }*/


    /**
     * Send a POST request to itcuties.com
     *
     * @param query
     * @throws IOException
     */
    public static String httpPost(String urlPath, String query) throws IOException {
        // Encode the query 
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        // This is the data that is going to be send to itcuties.com via POST request
        // 'e' parameter contains data to echo
        String postData = "e=" + encodedQuery;

        // Connect to google.com
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", String.valueOf(postData.length()));

        // Write data
        OutputStream os = connection.getOutputStream();
        os.write(postData.getBytes());

        // Read response
        StringBuilder responseSB = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while ((line = br.readLine()) != null)
            responseSB.append(line);

        // Close streams
        br.close();
        os.close();

        return responseSB.toString();

    }
}
