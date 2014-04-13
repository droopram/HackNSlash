package nl.antifraude.mijnid.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import nl.antifraude.mijnid.provider.Contract;
import nl.antifraude.mijnid.util.ParseUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 */
@DatabaseTable(tableName = Contract.Brief.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.Brief.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = Contract.MIME_NAME, type = Contract.Brief.MIME_TYPE)
public class Brief {

    @DatabaseField(generatedId = true, columnName = Contract.Brief._ID)
    private long id;

    @DatabaseField(columnName = Contract.Brief.ONDERWERP)
    private String onderwerp;
    @DatabaseField(columnName = Contract.Brief.VERZENDDATUM)
    private Date verzenddatum;
    @DatabaseField(columnName = Contract.Brief.ADRES)
    private String adres;
    @DatabaseField(columnName = Contract.Brief.POSTCODE)
    private String postcode;
    @DatabaseField(columnName = Contract.Brief.PLAATS)
    private String plaats;
    @DatabaseField(columnName = Contract.Brief.PRIORITEIT)
    private int prioriteit;

    public long getId() {
        return id;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public Date getVerzenddatum() {
        return verzenddatum;
    }

    public String getAdres() {
        return adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getPlaats() {
        return plaats;
    }

    public int getPrioriteit() {
        return prioriteit;
    }

    public static Brief fromJson(JSONObject json) throws JSONException {
        Brief brief = new Brief();
        brief.onderwerp = json.getString("onderwerp");
        brief.verzenddatum = ParseUtils.parseDateTime(json.getString("verzenddatum"));
        brief.adres = json.getString("adres");
        brief.postcode = json.getString("postcode");
        brief.plaats = json.getString("plaats");
        brief.prioriteit = json.getInt("prioriteit");
        return brief;
    }

    public static Brief fromCursor(Cursor cursor) {
        Brief brief = new Brief();
        brief.onderwerp = cursor.getString(cursor.getColumnIndex(Contract.Brief.ONDERWERP));
        if (!cursor.isNull(cursor.getColumnIndex(Contract.Brief.VERZENDDATUM))) {
            long longValue = cursor.getLong(cursor.getColumnIndex(Contract.Brief.VERZENDDATUM));
            brief.verzenddatum = new Date(longValue);
        }
        brief.adres = cursor.getString(cursor.getColumnIndex(Contract.Brief.ADRES));
        brief.postcode = cursor.getString(cursor.getColumnIndex(Contract.Brief.POSTCODE));
        brief.plaats = cursor.getString(cursor.getColumnIndex(Contract.Brief.PLAATS));
        brief.prioriteit = cursor.getInt(cursor.getColumnIndex(Contract.Brief.PRIORITEIT));
        return brief;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Brief.ONDERWERP, onderwerp);
        contentValues.put(Contract.Brief.VERZENDDATUM, verzenddatum.getTime());
        contentValues.put(Contract.Brief.ADRES, adres);
        contentValues.put(Contract.Brief.POSTCODE, postcode);
        contentValues.put(Contract.Brief.PLAATS, plaats);
        contentValues.put(Contract.Brief.PRIORITEIT, prioriteit);
        return contentValues;
    }
}
