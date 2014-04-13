package nl.antifraude.mijnid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Instantie {

    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String naam;
    @DatabaseField
    private String adres;
    @DatabaseField
    private String woonplaats;
    @DatabaseField
    private String postcode;
    @DatabaseField(uniqueIndex = true)
    private String kvknummer;
}
