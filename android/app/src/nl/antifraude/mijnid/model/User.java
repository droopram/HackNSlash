package nl.antifraude.mijnid.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;
import nl.antifraude.mijnid.provider.Contract;

/**
 * Created by jozua on 2014/04/12.
 */
@DatabaseTable(tableName = Contract.User.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentUri(authority = Contract.AUTHORITY, path = Contract.User.ENTITY_NAME)
@AdditionalAnnotation.DefaultContentMimeTypeVnd(name = Contract.MIME_NAME, type = Contract.User.MIME_TYPE)
public class User {

    // TODO relate to logged in user?

    @DatabaseField
    private String bsn;

    @DatabaseField
    private String voornaam;

    @DatabaseField
    private String achternaam;

    @DatabaseField
    private String geslacht; // M/V

    @DatabaseField
    private String straat;

    @DatabaseField
    private String gemeente;

    @DatabaseField
    private int huisnummer;

    @DatabaseField
    private String huisletter;

    @DatabaseField
    private String huisnummerToevoeging;

    @DatabaseField
    private String buitenlandAdres1;

    @DatabaseField
    private String buitenlandAdres2;

    @DatabaseField
    private String buitenlandAdres3;
}
