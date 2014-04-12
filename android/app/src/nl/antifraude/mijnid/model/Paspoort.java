package nl.antifraude.mijnid.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by jozua on 2014/04/12.
 */
public class Paspoort {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String documentnr;

    @DatabaseField
    private String vervaldatum; // TODO parse as datum

    @DatabaseField
    private String ontvangen; // TODO parse as datum + tijd

    @DatabaseField
    private String beschrijving;

}
