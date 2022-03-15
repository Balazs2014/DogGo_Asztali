package hu.doggo.doggo_admininterface.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Visszajelzes {
    private int id;
    private String comment;
    private Boolean read;
    private Date created_at;

    public Visszajelzes(int id, String comment, Boolean read, Date created_at) {
        this.id = id;
        this.read = read;
        this.comment = comment;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public Boolean isRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMM d.");
        return format.format(created_at);
    }

    public String getFormattedStatus() {
        String olvasva;
        if (read) {
            olvasva = "olvasva";
        } else {
            olvasva = "új";
        }

        return olvasva;
    }
}