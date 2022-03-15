package hu.doggo.doggo_admininterface.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.doggo.doggo_admininterface.Controller;
import hu.doggo.doggo_admininterface.classes.Helyszin;
import hu.doggo.doggo_admininterface.classes.Visszajelzes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class VisszajelzesApi extends Controller {
    private static final String API_URL = "http://127.0.0.1:8000/api";
    private static Gson jsonConverter = new Gson();

    public static List<Visszajelzes> getFeedbacks() throws IOException {
        String json = Api.get(API_URL + "/feedbacks");

        Type type = new TypeToken<List<Visszajelzes>>() {
        }.getType();
        return jsonConverter.fromJson(json, type);
    }

    public static List<Visszajelzes> getReadFeedbacks() throws IOException {
        String json = Api.get(API_URL + "/feedbacks_read");

        Type type = new TypeToken<List<Visszajelzes>>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }

    public static List<Visszajelzes> getNewFeedbacks() throws IOException {
        String json = Api.get(API_URL + "/feedbacks_not_read");

        Type type = new TypeToken<List<Visszajelzes>>() {
        }.getType();

        return jsonConverter.fromJson(json, type);
    }

    public static Visszajelzes updateReadFeedback(Visszajelzes modositando) throws IOException {
        String visszajelzesJson = jsonConverter.toJson(modositando);
        String json = Api.put(API_URL + "/feedbacks", modositando.getId(), visszajelzesJson);

        return jsonConverter.fromJson(json, Visszajelzes.class);
    }

    public static boolean deleteFeedback(int id) throws IOException {
        return Api.delete(API_URL + "/feedbacks", id).getResponseCode() == 204;
    }

    public static int getReadCount() throws IOException {
        String countString = Api.get(API_URL + "/read_feedback_count");
        return Integer.parseInt(countString);
    }

    public static int getNewCount() throws IOException {
        String countString = Api.get(API_URL + "/new_feedback_count");
        return Integer.parseInt(countString);
    }
}