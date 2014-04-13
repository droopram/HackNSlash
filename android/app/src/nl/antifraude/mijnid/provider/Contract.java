package nl.antifraude.mijnid.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 */
public class Contract {

    /**
     * The authority for the provider.
     */
    public static final String AUTHORITY = "nl.antifraude.mijnid.provider";

    /**
     * A content:// style uri to the authority for the provider
     */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static final String MIME_NAME = "nl.antifraude.mijnid.provider";


    public static class Event implements BaseColumns {

        /**
         * Identifier for the event resource. The entity name is used by
         * the {@link #CONTENT_URI} to allow access to this resource.
         */
        public static final String ENTITY_NAME = "event";

        /**
         * Base URI for the event resource.
         * <p>content://nl.antifraude.mijnid.provider/event</p>
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, ENTITY_NAME);

        public static final String MIME_TYPE = "events";

        public static final String DESCRIPTION = "description";
        public static final String SHORT_DESCRIPTION = "short_description";
        public static final String TIMESTAMP = "timestamp";
        public static final String PANIC_LEVEL = "panic_level";
    }

    public static class User implements BaseColumns {
        public static final String ENTITY_NAME = "user";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, ENTITY_NAME);
        public static final String MIME_TYPE = "users";

        public static final String BSN = "bsn";
        public static final String VOORNAAM = "voornaam";
        public static final String ACHTERNAAM = "achternaam";
        public static final String GESLACHT = "geslacht"; // M/V
        public static final String NATIONALITEIT = "nationaliteit";
        public static final String STRAAT = "straat";
        public static final String GEMEENTE = "gemeente";
        public static final String HUISNUMMER = "huisnummer";
        public static final String HUISLETTER = "huisletter";
        public static final String HUISNUMMER_TOEVOEGING = "huisnummerToevoeging";
        public static final String BUITENLAND_ADRES1 = "buitenlandAdres1";
        public static final String BUITENLAND_ADRES2 = "buitenlandAdres2";
        public static final String BUITENLAND_ADRES3 = "buitenlandAdres3";
    }

    public static class Brief implements BaseColumns {
        /**
         * Identifier for the event resource. The entity name is used by
         * the {@link #CONTENT_URI} to allow access to this resource.
         */
        public static final String ENTITY_NAME = "letter";

        /**
         * Base URI for the event resource.
         * <p>content://nl.antifraude.mijnid.provider/letter</p>
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, ENTITY_NAME);

        public static final String ONDERWERP = "onderwerp";
        public static final String VERZENDDATUM = "verzenddatum";
        public static final String ADRES = "adres";
        public static final String POSTCODE = "postcode";
        public static final String PLAATS = "plaats";
        public static final String PRIORITEIT = "prioriteit";
    }

    public static class PaspoortEvent implements BaseColumns {
        /**
         * Identifier for the event resource. The entity name is used by
         * the {@link #CONTENT_URI} to allow access to this resource.
         */
        public static final String ENTITY_NAME = "passport_event";

        /**
         * Base URI for the event resource.
         * <p>content://nl.antifraude.mijnid.provider/passport_events</p>
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(AUTHORITY_URI, ENTITY_NAME);

        public static final String DOCUMENTNR = "documentnr";
        public static final String VERVALDATUM = "vervaldatum";
        public static final String ONTVANGEN = "ontvangen";
        public static final String BESCHRIJVING = "beschrijving";
        public static final String INSTANTIE_KVK_NR = "instantieKvkNr";
    }
}
