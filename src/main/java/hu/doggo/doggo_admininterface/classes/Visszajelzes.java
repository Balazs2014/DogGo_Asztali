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

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getFormattedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy MMM d.");
        return format.format(created_at);
    }
}
