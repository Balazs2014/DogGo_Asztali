package hu.doggo.doggo_admininterface.controllers;

import hu.doggo.doggo_admininterface.api.HelyszinApi;
import hu.doggo.doggo_admininterface.classes.HelyszinErtekeles;

import java.io.IOException;

public class StatisztikakController {
    public void initialize(){
        try {
            HelyszinErtekeles legjobb = HelyszinApi.getLegjobbErtekeles();
            HelyszinErtekeles legrosszabb = HelyszinApi.getLegrosszabbErtekeles();
            System.out.println(legjobb);
            System.out.println(legrosszabb);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
