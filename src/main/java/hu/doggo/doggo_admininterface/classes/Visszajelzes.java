package hu.doggo.doggo_admininterface.classes;

import javafx.scene.control.CheckBox;

import java.util.Date;

public class Visszajelzes {
    private int id;
    private String comment;
    private int read;
    private Date created_at;

    public Visszajelzes(int id, String comment, int read, Date created_at) {
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

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
