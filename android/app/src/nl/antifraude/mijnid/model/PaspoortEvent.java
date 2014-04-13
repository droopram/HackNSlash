package nl.antifraude.mijnid.model;

import android.content.ContentValues;
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
@DatabaseTable(tableName = Contract.PaspoortEvent.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.PaspoortEvent.ENTITY_NAME)
public class PaspoortEvent {

    @DatabaseField(generatedId = true, columnName = Contract.PaspoortEvent._ID)
    private long id;

    @DatabaseField()
    private String documentnr;

    @DatabaseField
    private Date vervaldatum;

    @DatabaseField
    private Date ontvangen;

    @DatabaseField
    private String beschrijving;

    @DatabaseField
    private String instantieKvkNr;

    public long getId() {
        return id;
    }

    public String getDocumentnr() {
        return documentnr;
    }

    public Date getVervaldatum() {
        return vervaldatum;
    }

    public Date getOntvangen() {
        return ontvangen;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public static PaspoortEvent fromJson(JSONObject json) throws JSONException {
        PaspoortEvent paspoortEvent = new PaspoortEvent();
        paspoortEvent.documentnr = json.getString("documentnr");
        paspoortEvent.vervaldatum = ParseUtils.parseDate(json.getString("vervaldatum"));
        paspoortEvent.ontvangen = ParseUtils.parseDateTime(json.getString("ontvangen"));
        paspoortEvent.beschrijving = json.getString("beschrijving");
        paspoortEvent.instantieKvkNr = json.getJSONObject("instantie").getString("kvknummer");
        return paspoortEvent;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.PaspoortEvent.DOCUMENTNR, documentnr);
        contentValues.put(Contract.PaspoortEvent.VERVALDATUM, vervaldatum.getTime());
        contentValues.put(Contract.PaspoortEvent.ONTVANGEN, ontvangen.getTime());
        contentValues.put(Contract.PaspoortEvent.BESCHRIJVING, beschrijving);
        contentValues.put(Contract.PaspoortEvent.INSTANTIE_KVK_NR, instantieKvkNr);
        return contentValues;
    }
}
