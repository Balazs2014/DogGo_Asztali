package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.classes.Ertekeles;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ErtekelesApi {
    //private static final String API_URL = "http://127.0.0.1:8000/api";
    private static final String API_URL = "http://doggobackend.ddns.net/DogGo_Backend/server.php/api";
    private static Gson jsonConverter = new Gson();

    public static List<Ertekeles> getUserRatings(int id) throws IOException {
        String json = Api.get(API_URL + "/rating_by_user/" + id);

        Type type = new TypeToken<List<Ertekeles>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static Ertekeles deleteDescription(Ertekeles ertekeles) throws IOException {
        String ertekelesJson = jsonConverter.toJson(ertekeles);
        String json = Api.put(API_URL + "/ratings", ertekeles.getId(), ertekelesJson);

        return jsonConverter.fromJson(json, Ertekeles.class);
    }
}