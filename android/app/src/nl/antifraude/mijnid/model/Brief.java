package nl.antifraude.mijnid.model;

import android.content.ContentValues;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nl.antifraude.mijnid.provider.Contract;
import nl.antifraude.mijnid.util.ParseUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 */
@DatabaseTable
public class Brief {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private String onderwerp;
    @DatabaseField
    private Date verzenddatum;
    @DatabaseField
    private String adres;
    @DatabaseField
    private String postcode;
    @DatabaseField
    private String plaats;
    @DatabaseField
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
