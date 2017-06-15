package com.example.emiliekvist.kvitit;

import java.util.Date;

/**
 * Created by EmilieKvist on 15-06-2017.
 */

public class Kvittering {

    public Date dato;
    public Date udløbsDato;
    public String[] tags;
    public String photoPath;

    public Kvittering(Date dato, Date udløbsDato, String[] tags, String photoPath) {
        this.dato = dato;
        this.udløbsDato = udløbsDato;
        this.tags = tags;
        this.photoPath = photoPath;
    }


}
