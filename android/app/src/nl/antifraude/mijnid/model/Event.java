package nl.antifraude.mijnid.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import nl.antifraude.mijnid.provider.Contract;
import nl.antifraude.mijnid.util.ParseUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 */
@DatabaseTable(tableName = Contract.Event.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.Event.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = Contract.MIME_NAME, type = Contract.Event.MIME_TYPE)
public class Event {

    @DatabaseField(generatedId = true, columnName = Contract.Event._ID)
    private long id;
    @DatabaseField(columnName = Contract.Event.DESCRIPTION)
    private String description;
    @DatabaseField(columnName = Contract.Event.SHORT_DESCRIPTION)
    private String shortDescription;
    @DatabaseField(columnName = Contract.Event.TIMESTAMP, dataType = DataType.DATE_LONG)
    private Date timestamp;
    @DatabaseField(columnName = Contract.Event.PANIC_LEVEL)
    private int panicLevel;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public static Event fromJson(JSONObject object) throws JSONException {
        Event event = new Event();
        event.shortDescription = object.getString("short_desc");
        event.description = object.getString("desc");
        event.timestamp = ParseUtils.parseDateTime(object.getString("date"));
        return event;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.Event.TIMESTAMP, timestamp.getTime());
        contentValues.put(Contract.Event.DESCRIPTION, description);
        contentValues.put(Contract.Event.SHORT_DESCRIPTION, shortDescription);
        return contentValues;
    }

    public static Event fromCursor(Cursor cursor) {
        Event event = new Event();
        event.description = cursor.getString(cursor.getColumnIndex(Contract.Event.DESCRIPTION));
        event.shortDescription = cursor.getString(cursor.getColumnIndex(Contract.Event.SHORT_DESCRIPTION));
        event.timestamp = new Date(cursor.getLong(cursor.getColumnIndex(Contract.Event.TIMESTAMP)));
        return event;
    }
}
