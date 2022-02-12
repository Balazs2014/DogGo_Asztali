package hu.doggo.doggo_admininterface.classes;

import java.util.Date;

public class Visszajelzes {
    private int id;
    private String comment;
    private Date created_at;

    public Visszajelzes(int id, String comment, Date created_at) {
        this.id = id;
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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
