package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.RequestHandler;
import hu.doggo.doggo_admininterface.Response;

import java.io.IOException;

public class Api extends Controller {
    private static Gson jsonConverter = new Gson();

    public static String get(String url) throws IOException {
        Response response = RequestHandler.get(url);
        String json = response.getContent();
        Gson jsonConverter = new Gson();

        if (response.getResponseCode() >= 400) {
            String message = jsonConverter.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }

        return json;
    }

    public static String put(String url, int id, String jsonModositando) throws IOException {
        Response response = RequestHandler.put(url + "/" + id, jsonModositando);
        String json = response.getContent();

        if (response.getResponseCode() >= 400) {
            String message = jsonConverter.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }

        return json;
    }
}