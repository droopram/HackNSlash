package nl.antifraude.mijnid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import nl.antifraude.mijnid.provider.Contract;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
