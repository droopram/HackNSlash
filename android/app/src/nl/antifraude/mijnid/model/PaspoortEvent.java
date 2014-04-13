package nl.antifraude.mijnid.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.j256.ormlite.field.DataType;
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
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = Contract.MIME_NAME, type = Contract.PaspoortEvent.MIME_TYPE)
public class PaspoortEvent {

    @DatabaseField(generatedId = true, columnName = Contract.PaspoortEvent._ID)
    private long id;

    @DatabaseField(columnName = Contract.PaspoortEvent.DOCUMENTNR)
    private String documentnr;

    @DatabaseField(columnName = Contract.PaspoortEvent.VERVALDATUM, dataType = DataType.DATE_LONG)
    private Date vervaldatum;

    @DatabaseField(columnName = Contract.PaspoortEvent.ONTVANGEN, dataType = DataType.DATE_LONG)
    private Date ontvangen;

    @DatabaseField(columnName = Contract.PaspoortEvent.BESCHRIJVING)
    private String beschrijving;

    @DatabaseField(columnName = Contract.PaspoortEvent.INSTANTIE_KVK_NR)
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

    public static PaspoortEvent fromCursor(Cursor cursor) {
        PaspoortEvent paspoortEvent = new PaspoortEvent();
        paspoortEvent.documentnr = cursor.getString(cursor.getColumnIndex(Contract.PaspoortEvent.DOCUMENTNR));
        if (!cursor.isNull(cursor.getColumnIndex(Contract.PaspoortEvent.VERVALDATUM))) {
            long longValue = cursor.getLong(cursor.getColumnIndex(Contract.PaspoortEvent.VERVALDATUM));
            paspoortEvent.vervaldatum = new Date(longValue);
        }
        if (!cursor.isNull(cursor.getColumnIndex(Contract.PaspoortEvent.ONTVANGEN))) {
            long longValue = cursor.getLong(cursor.getColumnIndex(Contract.PaspoortEvent.ONTVANGEN));
            paspoortEvent.ontvangen = new Date(longValue);
        }
        paspoortEvent.beschrijving = cursor.getString(cursor.getColumnIndex(Contract.PaspoortEvent.BESCHRIJVING));
        paspoortEvent.instantieKvkNr = cursor.getString(cursor.getColumnIndex(Contract.PaspoortEvent.INSTANTIE_KVK_NR));
        return paspoortEvent;
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
        if (vervaldatum != null) {
            contentValues.put(Contract.PaspoortEvent.VERVALDATUM, vervaldatum.getTime());
        }
        if (ontvangen != null) {
            contentValues.put(Contract.PaspoortEvent.ONTVANGEN, ontvangen.getTime());
        }
        contentValues.put(Contract.PaspoortEvent.BESCHRIJVING, beschrijving);
        contentValues.put(Contract.PaspoortEvent.INSTANTIE_KVK_NR, instantieKvkNr);
        return contentValues;
    }
}
