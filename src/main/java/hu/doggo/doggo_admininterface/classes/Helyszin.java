package hu.doggo.doggo_admininterface.classes;

public class Helyszin {
    private int id;
    private String name;
    private String description;
    private double lat;
    private double lng;
    private int user_id;
    private boolean allowed;

    public Helyszin(int id, String name, String description, double lat, double lng, int user_id, boolean allowed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
        this.user_id = user_id;
        this.allowed = allowed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getFormattedStatus() {
        String engedelyezve;
        if (allowed) {
            engedelyezve = "engedélyezve";
        } else {
            engedelyezve = "új";
        }

        return engedelyezve;
    }
}
