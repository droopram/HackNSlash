package nl.antifraude.mijnid.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import nl.antifraude.mijnid.provider.Contract;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
@DatabaseTable(tableName = Contract.User.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.User.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = Contract.MIME_NAME, type = Contract.User.MIME_TYPE)
public class User {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(columnName = Contract.User.BSN)
    private String bsn;

    @DatabaseField(columnName = Contract.User.VOORNAAM)
    private String voornaam;

    @DatabaseField(columnName = Contract.User.ACHTERNAAM)
    private String achternaam;

    @DatabaseField(columnName = Contract.User.GESLACHT)
    private String geslacht; // M/V

    @DatabaseField(columnName = Contract.User.NATIONALITEIT)
    private String nationaliteit;

    @DatabaseField(columnName = Contract.User.STRAAT)
    private String straat;

    @DatabaseField(columnName = Contract.User.GEMEENTE)
    private String gemeente;

    @DatabaseField(columnName = Contract.User.HUISNUMMER)
    private int huisnummer;

    @DatabaseField(columnName = Contract.User.HUISLETTER)
    private String huisletter;

    @DatabaseField(columnName = Contract.User.HUISNUMMER_TOEVOEGING)
    private String huisnummerToevoeging;

    @DatabaseField(columnName = Contract.User.BUITENLAND_ADRES1)
    private String buitenlandAdres1;

    @DatabaseField(columnName = Contract.User.BUITENLAND_ADRES2)
    private String buitenlandAdres2;

    @DatabaseField(columnName = Contract.User.BUITENLAND_ADRES3)
    private String buitenlandAdres3;

    public long getId() {
        return id;
    }

    public String getBsn() {
        return bsn;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getGeslacht() {
        return geslacht;
    }

    public String getNationaliteit() {
        return nationaliteit;
    }

    public String getStraat() {
        return straat;
    }

    public String getGemeente() {
        return gemeente;
    }

    public int getHuisnummer() {
        return huisnummer;
    }

    public String getHuisletter() {
        return huisletter;
    }

    public String getHuisnummerToevoeging() {
        return huisnummerToevoeging;
    }

    public String getBuitenlandAdres1() {
        return buitenlandAdres1;
    }

    public String getBuitenlandAdres2() {
        return buitenlandAdres2;
    }

    public String getBuitenlandAdres3() {
        return buitenlandAdres3;
    }

    public static User fromJson(JSONObject object) throws JSONException {
        User user = new User();
        user.bsn = object.getString("bsn");
        user.voornaam = object.getString("voornaam");
        user.achternaam = object.getString("achternaam");
        user.geslacht = object.getString("geslacht");
        user.nationaliteit = object.getString("nationaliteit");
        user.gemeente = object.getString("gemeente");
        user.straat = object.getString("straat");
        user.huisnummer = object.getInt("huisnummer");
        user.huisletter = object.getString("huisletter");
        user.huisnummerToevoeging = object.getString("huisnummer_toevoeging");
        user.buitenlandAdres1 = object.getString("buitenland_adres_1");
        user.buitenlandAdres2 = object.getString("buitenland_adres_2");
        user.buitenlandAdres3 = object.getString("buitenland_adres_3");
        return user;
    }

    public static User fromCursor(Cursor cursor) {
        User user = new User();
        user.bsn = cursor.getString(cursor.getColumnIndex(Contract.User.BSN));
        user.voornaam = cursor.getString(cursor.getColumnIndex(Contract.User.VOORNAAM));
        user.achternaam = cursor.getString(cursor.getColumnIndex(Contract.User.ACHTERNAAM));
        user.geslacht = cursor.getString(cursor.getColumnIndex(Contract.User.GESLACHT));
        user.nationaliteit = cursor.getString(cursor.getColumnIndex(Contract.User.NATIONALITEIT));
        user.gemeente = cursor.getString(cursor.getColumnIndex(Contract.User.GEMEENTE));
        user.straat = cursor.getString(cursor.getColumnIndex(Contract.User.STRAAT));
        user.huisnummer = cursor.getInt(cursor.getColumnIndex(Contract.User.HUISNUMMER));
        user.huisletter = cursor.getString(cursor.getColumnIndex(Contract.User.HUISLETTER));
        user.huisnummerToevoeging = cursor.getString(cursor.getColumnIndex(Contract.User.HUISNUMMER_TOEVOEGING));
        user.buitenlandAdres1 = cursor.getString(cursor.getColumnIndex(Contract.User.BUITENLAND_ADRES1));
        user.buitenlandAdres2 = cursor.getString(cursor.getColumnIndex(Contract.User.BUITENLAND_ADRES2));
        user.buitenlandAdres3 = cursor.getString(cursor.getColumnIndex(Contract.User.BUITENLAND_ADRES3));
        return user;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.User.BSN, bsn);
        contentValues.put(Contract.User.VOORNAAM, voornaam);
        contentValues.put(Contract.User.ACHTERNAAM, achternaam);
        contentValues.put(Contract.User.GESLACHT, geslacht);
        contentValues.put(Contract.User.NATIONALITEIT, nationaliteit);
        contentValues.put(Contract.User.GEMEENTE, gemeente);
        contentValues.put(Contract.User.STRAAT, straat);
        contentValues.put(Contract.User.HUISNUMMER, huisnummer);
        contentValues.put(Contract.User.HUISLETTER, huisletter);
        contentValues.put(Contract.User.HUISNUMMER_TOEVOEGING, huisnummerToevoeging);
        contentValues.put(Contract.User.BUITENLAND_ADRES1, buitenlandAdres1);
        contentValues.put(Contract.User.BUITENLAND_ADRES2, buitenlandAdres2);
        contentValues.put(Contract.User.BUITENLAND_ADRES3, buitenlandAdres3);
        return contentValues;
    }
}
