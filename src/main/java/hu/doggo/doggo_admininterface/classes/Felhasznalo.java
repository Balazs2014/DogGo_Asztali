package hu.doggo.doggo_admininterface.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Felhasznalo {
    private int id;
    private String username;
    private String email;
    private Date created_at;
    private int permission;

    public Felhasznalo(int id, String username, String email, Date created_at, int permission) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.created_at = created_at;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public String getFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMM d.");
        return format.format(created_at);
    }

    public String getFormattedPermission() {
        String jog;
        switch (permission) {
            case 1:
                jog = "tiltva";
                break;
            case 2:
                jog = "admin";
                break;
            case 3:
                jog = "szuperadmin";
                break;
            default:
                jog = "default";
        }

        return jog;
    }
}
