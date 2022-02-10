package hu.doggo.doggo_admininterface.classes;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}