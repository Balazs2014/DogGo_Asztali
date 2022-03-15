package hu.doggo.doggo_admininterface.classes;

public class Ertekeles {
    private int id;
    private String description;
    private int stars;

    public Ertekeles(int id, String description, int stars) {
        this.id = id;
        this.description = description;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }
}
