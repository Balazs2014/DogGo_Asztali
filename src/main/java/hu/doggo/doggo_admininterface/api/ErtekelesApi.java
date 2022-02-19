package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Ertekeles;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ErtekelesApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api/ratings";
    private static Gson jsonConverter = new Gson();

    public static List<Ertekeles> getErtekelesek() throws IOException {
        String json = Api.get(API_URL);

        Type type = new TypeToken<List<Ertekeles>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static Ertekeles updateLeiras(Ertekeles modositando) throws IOException {
        String ertekelesJson = jsonConverter.toJson(modositando);
        String json = Api.put(API_URL, modositando.getId(), ertekelesJson);

        return jsonConverter.fromJson(json, Ertekeles.class);
    }
}
