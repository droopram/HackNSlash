package nl.antifraude.mijnid.provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MimeTypeVnd;
import nl.antifraude.mijnid.model.Event;

/**
 */
public class Provider extends OrmLiteSimpleContentProvider<DatabaseHelper> {

    private static final int URI_CODE_EVENT_MANY = 1;
    private static final int URI_CODE_EVENT_ONE = 2;


    @Override
    public boolean onCreate() {
        setMatcherController(new MatcherController()
                        .add(Event.class, MimeTypeVnd.SubType.DIRECTORY, "", URI_CODE_EVENT_MANY)
                        .add(Event.class, MimeTypeVnd.SubType.ITEM, "#", URI_CODE_EVENT_ONE)
        );
        return true;
    }

    @Override
    protected Class<DatabaseHelper> getHelperClass() {
        return DatabaseHelper.class;
    }
}
