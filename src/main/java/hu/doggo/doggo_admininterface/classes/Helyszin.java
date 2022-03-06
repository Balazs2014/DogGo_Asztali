package hu.doggo.doggo_admininterface.classes;

public class Helyszin {
    private int id;
    private String name;
    private String description;
    private double lat;
    private double lng;
    private boolean allowed;

    public Helyszin(int id, String name, String description, double lat, double lng, boolean allowed) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
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

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getFormattedAllowed() {
        String engedelyezve;
        if (allowed) {
            engedelyezve = "engedélyezve";
        } else {
            engedelyezve = "nincs";
        }

        return engedelyezve;
    }
}
