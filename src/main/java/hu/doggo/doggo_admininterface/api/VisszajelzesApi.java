package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.RequestHandler;
import hu.doggo.doggo_admininterface.Response;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class VisszajelzesApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api/feedbacks";

    public static List<Visszajelzes> getVisszajelzesek() throws IOException {
        Response response = RequestHandler.get(API_URL);
        String json = response.getContent();
        Gson jsonConverter = new Gson();
        if (response.getResponseCode() >= 400) {
            String message = jsonConverter.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }

        Type type = new TypeToken<List<Visszajelzes>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }
}
