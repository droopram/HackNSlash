package nl.antifraude.mijnid.provider;

import android.net.Uri;
import android.provider.BaseColumns;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation;

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


        public static final String DESCRIPTION = "description";
        public static final String SHORT_DESCRIPTION = "short_description";
    }
}
