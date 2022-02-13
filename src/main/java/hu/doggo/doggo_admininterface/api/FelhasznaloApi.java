package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FelhasznaloApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api/users";
    private static Gson jsonConverter = new Gson();

    public static List<Felhasznalo> getFelhasznalok() throws IOException {
        String json = Api.get(API_URL);
        Type type = new TypeToken<List<Felhasznalo>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static Felhasznalo updateFelhasznalo(Felhasznalo modositando) throws IOException {
        String felhasznaloJson = jsonConverter.toJson(modositando);
        String json = Api.put(API_URL, modositando.getId(), felhasznaloJson);

        return jsonConverter.fromJson(json, Felhasznalo.class);
    }
}
