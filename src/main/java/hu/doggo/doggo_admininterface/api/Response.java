package hu.doggo.doggo_admininterface.api;

public class Response {
    int responseCode;
    String content;

    public Response(int responseCode, String content) {
        this.responseCode = responseCode;
        this.content = content;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getContent() {
        return content;
    }
}