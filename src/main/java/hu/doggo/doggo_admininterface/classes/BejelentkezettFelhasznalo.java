package hu.doggo.doggo_admininterface.classes;


public class BejelentkezettFelhasznalo {
    private String username;
    private String email;
    private int permission;

    public BejelentkezettFelhasznalo(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public int getPermission() {
        return permission;
    }
}
