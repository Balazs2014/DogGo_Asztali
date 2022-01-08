package hu.doggo.doggo_admininterface;

import java.time.LocalDate;
import java.util.Date;

public class Felhasznalo {
    private int id;
    private String felhasznalonev;
    private String email;
    private LocalDate regisztracio_datum;
    private Boolean admin;
    private Boolean tiltva;

    public Felhasznalo(int id, String felhasznalonev, String email, LocalDate regisztracio_datum, Boolean admin, Boolean tiltva) {
        this.id = id;
        this.felhasznalonev = felhasznalonev;
        this.email = email;
        this.regisztracio_datum = regisztracio_datum;
        this.admin = admin;
        this.tiltva = tiltva;
    }

    public int getId() {
        return id;
    }

    public String getFelhasznalonev() {
        return felhasznalonev;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegisztracio_datum() {
        return regisztracio_datum;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public Boolean getTiltva() {
        return tiltva;
    }
}
