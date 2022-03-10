package hu.doggo.doggo_admininterface.classes;

public class HelyszinErtekeles {
    private String name;
    private double atlag;

    public HelyszinErtekeles(String name, double atlag) {
        this.name = name;
        this.atlag = atlag;
    }

    public String getName() {
        return name;
    }

    public double getAtlag() {
        return atlag;
    }
}
