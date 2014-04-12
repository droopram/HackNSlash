package nl.antifraude.mijnid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import nl.antifraude.mijnid.provider.Contract;

/**
 */
@DatabaseTable
public class Event {

    @DatabaseField(generatedId = true, columnName = Contract.Event._ID)
    private long id;
    @DatabaseField(columnName = Contract.Event.DESCRIPTION)
    private String description;
    @DatabaseField(columnName = Contract.Event.SHORT_DESCRIPTION)
    private String shortDescription;
}
