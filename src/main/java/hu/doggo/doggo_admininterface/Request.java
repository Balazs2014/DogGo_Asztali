package hu.doggo.doggo_admininterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Request {
    public static final String BASE_URL = "https://retoolapi.dev/b9Ou4n/doggoAPI";

    public static String getData() throws IOException {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + responseCode);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String s = "";
        String output;
        while ((output = br.readLine()) != null) {
            s += output;
            s += System.lineSeparator();
        }

        return s;
    }
}
