package hu.doggo.doggo_admininterface.api;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestHandler {
    private RequestHandler() {
    }

    public static Response get(String url) throws IOException {
        HttpURLConnection conn = setConnection(url);

        return getResponse(conn);
    }

    public static Response post(String url, String data) throws IOException {
        HttpURLConnection conn = setConnection(url);

        conn.setRequestMethod("POST");
        addRequestBody(conn, data);

        return getResponse(conn);
    }

    public static Response put(String url, String data) throws IOException {
        HttpURLConnection conn = setConnection(url);

        conn.setRequestMethod("PUT");
        addRequestBody(conn, data);

        return getResponse(conn);
    }

    public static Response delete(String url) throws IOException {
        HttpURLConnection conn = setConnection(url);

        conn.setRequestMethod("DELETE");

        return getResponse(conn);
    }

    private static Response getResponse(HttpURLConnection conn) throws IOException {
        int responseCode = conn.getResponseCode();
        InputStream is;
        if (responseCode < 400) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String sor = reader.readLine();
        while (sor != null) {
            builder.append(sor);
            sor = reader.readLine();
        }
        reader.close();
        is.close();

        return new Response(responseCode, builder.toString());
    }

    private static HttpURLConnection setConnection(String url) throws IOException {
        URL urlObject = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);

        return conn;
    }

    private static void addRequestBody(HttpURLConnection conn, String data) throws IOException {
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(data);
        writer.flush();
        writer.close();
        os.close();
    }
}