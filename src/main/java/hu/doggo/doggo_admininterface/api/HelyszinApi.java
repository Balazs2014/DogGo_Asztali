package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Helyszin;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class HelyszinApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api/locations";
    private static Gson jsonConverter = new Gson();

    public static List<Helyszin> getHelyszin() throws IOException {
        String json = Api.get(API_URL);

        Type type = new TypeToken<List<Helyszin>>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }
}
