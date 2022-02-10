package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.RequestHandler;
import hu.doggo.doggo_admininterface.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FelhasznaloApi extends Controller {
    private static final String FELH_API_URL = "http://127.0.0.1:8000/api/users";

    public static List<Felhasznalo> getFelhasznalok() throws IOException {
        Response response = RequestHandler.get(FELH_API_URL);
        String json = response.getContent();
        Gson jsonConverter = new Gson();
        if (response.getResponseCode() >= 400) {
            String message = jsonConverter.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }

        Type type = new TypeToken<List<Felhasznalo>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static Felhasznalo updateFelhasznalo(Felhasznalo modositando) throws IOException {
        Gson jsonConverter = new Gson();
        String felhasznaloJson = jsonConverter.toJson(modositando);
        Response response = RequestHandler.put(FELH_API_URL + "/" + modositando.getId(), felhasznaloJson);
        String json = response.getContent();
        if (response.getResponseCode() >= 400) {
            String message = jsonConverter.fromJson(json, ApiError.class).getMessage();
            throw new IOException(message);
        }
        return jsonConverter.fromJson(json, Felhasznalo.class);
    }
}
