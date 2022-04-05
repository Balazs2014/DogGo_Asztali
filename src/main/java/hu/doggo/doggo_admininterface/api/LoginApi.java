package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.classes.Felhasznalo;
import hu.doggo.doggo_admininterface.classes.Login;
import hu.doggo.doggo_admininterface.classes.Token;

import java.io.IOException;
import java.lang.reflect.Type;

public class LoginApi {
    //private static final String API_URL = "http://127.0.0.1:8000/api";
    private static final String API_URL = "http://doggobackend.ddns.net/DogGo_Backend/server.php/api";
    private static Gson jsonConverter = new Gson();

    public static Token postLogin(Login login) throws IOException {
        String loginJson = jsonConverter.toJson(login);
        String json = Api.post(API_URL + "/login", loginJson);
        return jsonConverter.fromJson(json, Token.class);
    }

    public static Felhasznalo getLoginData(String token) throws IOException {
        String json = Api.getLogin(API_URL + "/user", token);
        Type type = new TypeToken<Felhasznalo>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }
}