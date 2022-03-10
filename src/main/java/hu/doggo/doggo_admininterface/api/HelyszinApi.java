package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Helyszin;
import hu.doggo.doggo_admininterface.classes.HelyszinErtekeles;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class HelyszinApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api";
    private static Gson jsonConverter = new Gson();

    public static List<Helyszin> getHelyszin() throws IOException {
        String json = Api.get(API_URL + "/locations");

        Type type = new TypeToken<List<Helyszin>>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }

    public static List<Helyszin> getEngedelyezettHelyszin() throws IOException {
        String json = Api.get(API_URL + "/locations_allowed");

        Type type = new TypeToken<List<Helyszin>>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }

    public static List<Helyszin> getNemEngedelyezettHelyszin() throws IOException {
        String json = Api.get(API_URL + "/locations_not_allowed");

        Type type = new TypeToken<List<Helyszin>>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }

    public static Helyszin updateHelyszin(Helyszin modositando) throws IOException {
        String helyszinJson = jsonConverter.toJson(modositando);
        String json = Api.put(API_URL + "/locations", modositando.getId(), helyszinJson);

        return jsonConverter.fromJson(json, Helyszin.class);
    }

    public static boolean deleteHelyszin(int id) throws IOException {
        return Api.delete(API_URL + "/locations", id).getResponseCode() == 204;
    }

    public static HelyszinErtekeles getLegjobbErtekeles() throws IOException {
        String json = Api.get(API_URL + "/best_rating");

        Type type = new TypeToken<HelyszinErtekeles>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }

    public static HelyszinErtekeles getLegrosszabbErtekeles() throws IOException {
        String json = Api.get(API_URL + "/worst_rating");

        Type type = new TypeToken<HelyszinErtekeles>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }

    public static int getAllowedCount() throws IOException {
        String countString = Api.get(API_URL + "/allowed_location_count");
        return Integer.parseInt(countString);
    }

    public static int getNewCount() throws IOException {
        String countString = Api.get(API_URL + "/new_location_count");
        return Integer.parseInt(countString);
    }
}