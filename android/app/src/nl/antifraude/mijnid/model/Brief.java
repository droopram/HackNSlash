package nl.antifraude.mijnid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by jozua on 2014/04/12.
 */
@DatabaseTable
public class Brief {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String onderwerp;
    @DatabaseField
    private String verzenddatum; // TODO parse as datum
    @DatabaseField
    private String adres;
    @DatabaseField
    private String postcode;
    @DatabaseField
    private String plaats;
    @DatabaseField
    private String prioriteit; // TODO ask is int?

    public long getId() {
        return id;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public String getVerzenddatum() {
        return verzenddatum;
    }

    public void setVerzenddatum(String verzenddatum) {
        this.verzenddatum = verzenddatum;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getPrioriteit() {
        return prioriteit;
    }

    public void setPrioriteit(String prioriteit) {
        this.prioriteit = prioriteit;
    }
}
