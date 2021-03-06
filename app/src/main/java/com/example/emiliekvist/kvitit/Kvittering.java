package com.example.emiliekvist.kvitit;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by EmilieKvist on 15-06-2017.
 */
//Kvittering objekt
public class Kvittering extends RealmObject {

    public Date dato;
    public Date udløbsDato;
    public String tags;
    public String photoPath;

    public Kvittering() {}

    public Kvittering(Date dato, Date udløbsDato, String tags, String photoPath) {
        this.dato = dato;
        this.udløbsDato = udløbsDato;
        this.tags = tags;
        this.photoPath = photoPath;
    }
}
