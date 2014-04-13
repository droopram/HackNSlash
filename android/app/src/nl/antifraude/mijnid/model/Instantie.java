package nl.antifraude.mijnid.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import nl.antifraude.mijnid.provider.Contract;
import org.json.JSONException;
import org.json.JSONObject;

@DatabaseTable
@AdditionalAnnotation.DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.Instantie.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = Contract.MIME_NAME, type = Contract.Instantie.MIME_TYPE)
public class Instantie {

    @DatabaseField(generatedId = true, columnName = Contract.Instantie._ID)
    private long id;
    @DatabaseField(columnName = Contract.Instantie.NAAM)
    private String naam;
    @DatabaseField(columnName = Contract.Instantie.ADRES)
    private String adres;
    @DatabaseField(columnName = Contract.Instantie.WOONPLAATS)
    private String woonplaats;
    @DatabaseField(columnName = Contract.Instantie.POSTCODE)
    private String postcode;
    @DatabaseField(uniqueIndex = true, columnName = Contract.Instantie.KVKNUMMER)
    private String kvknummer;

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getAdres() {
        return adres;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getKvknummer() {
        return kvknummer;
    }

    public static Instantie fromCursor(Cursor cursor) {
        Instantie instantie = new Instantie();
        instantie.naam = cursor.getString(cursor.getColumnIndex(Contract.Instantie.NAAM));
        instantie.adres = cursor.getString(cursor.getColumnIndex(Contract.Instantie.ADRES));
        instantie.woonplaats = cursor.getString(cursor.getColumnIndex("woonplaats"));
        instantie.postcode = cursor.getString(cursor.getColumnIndex("postcode"));
        instantie.kvknummer = cursor.getString(cursor.getColumnIndex("kvknummer"));
        return instantie;
    }

    public static Instantie fromJson(JSONObject json) throws JSONException {
        Instantie instantie = new Instantie();
        instantie.naam = json.getString("naam");
        instantie.adres = json.getString("adres");
        instantie.woonplaats = json.getString("woonplaats");
        instantie.postcode = json.getString("postcode");
        instantie.kvknummer = json.getString("kvknummer");
        return instantie;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Instantie.NAAM, naam);
        contentValues.put(Contract.Instantie.ADRES, adres);
        contentValues.put(Contract.Instantie.WOONPLAATS, woonplaats);
        contentValues.put(Contract.Instantie.POSTCODE, postcode);
        contentValues.put(Contract.Instantie.KVKNUMMER, kvknummer);
        return contentValues;
    }
}
