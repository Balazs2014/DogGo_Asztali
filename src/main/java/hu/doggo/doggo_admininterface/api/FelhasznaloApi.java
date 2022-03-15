package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FelhasznaloApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api";
    private static Gson jsonConverter = new Gson();

    public static List<Felhasznalo> getUsers() throws IOException {
        String json = Api.get(API_URL + "/users");
        Type type = new TypeToken<List<Felhasznalo>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static Felhasznalo updateUserPermission(Felhasznalo felhasznalo) throws IOException {
        String felhasznaloJson = jsonConverter.toJson(felhasznalo);
        String json = Api.put(API_URL + "/users", felhasznalo.getId(), felhasznaloJson);

        return jsonConverter.fromJson(json, Felhasznalo.class);
    }

    public static int getUsersCount() throws IOException {
        String countString = Api.get(API_URL + "/user_count");
        return Integer.parseInt(countString);
    }

    public static int getBannedCount() throws IOException {
        String countString = Api.get(API_URL + "/banned_user_count");
        return Integer.parseInt(countString);
    }

    public static int getAdminCount() throws IOException {
        String countString = Api.get(API_URL + "/admin_user_count");
        return Integer.parseInt(countString);
    }
}