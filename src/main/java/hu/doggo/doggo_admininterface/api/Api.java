package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;

import java.io.IOException;

public class Api {
    private static Gson jsonConverter = new Gson();

    public static String get(String url) throws IOException {
        Response response = RequestHandler.get(url);
        String json = response.getContent();

        if (response.getResponseCode() >= 400) {
            String message = jsonConverter.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }

        return json;
    }

    public static String post(String url, String ujJson) throws IOException {
        Response response = RequestHandler.post(url, ujJson);
        String json = response.getContent();
        if (response.getResponseCode() > 400) {
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

    public static Response delete(String url, int id) throws IOException {
        Response respone = RequestHandler.delete(url + "/" + id);
        String json = respone.getContent();
        if (respone.getResponseCode() > 400) {
            ApiError hiba = jsonConverter.fromJson(json, ApiError.class);
            String msg = hiba.getMessage();
            throw new IOException(msg);
        }
        return respone;
    }

    public static String getLogin(String url, String token) throws IOException {
        Response response = RequestHandler.tokenAuthorization(url, token);
        String json = response.getContent();

        if (response.getResponseCode() >= 400) {
            String message = jsonConverter.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }

        return json;
    }
}