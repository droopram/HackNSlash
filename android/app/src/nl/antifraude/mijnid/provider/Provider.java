package nl.antifraude.mijnid.provider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MatcherPattern;
import com.tojc.ormlite.android.framework.MimeTypeVnd;
import com.tojc.ormlite.android.framework.OperationParameters;
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
    public Cursor onQuery(DatabaseHelper helper, SQLiteDatabase db, MatcherPattern target, OperationParameters.QueryParameters parameter) {
        Cursor cursor = super.onQuery(helper, db, target, parameter);
        cursor.setNotificationUri(getContext().getContentResolver(), parameter.getUri());
        return cursor;
    }

    @Override
    public Uri onInsert(DatabaseHelper helper, SQLiteDatabase db, MatcherPattern target, OperationParameters.InsertParameters parameter) {
        Uri uri = super.onInsert(helper, db, target, parameter);
        getContext().getContentResolver().notifyChange(parameter.getUri(), null);
        return uri;
    }

    @Override
    public int onUpdate(DatabaseHelper helper, SQLiteDatabase db, MatcherPattern target, OperationParameters.UpdateParameters parameter) {
        int result = super.onUpdate(helper, db, target, parameter);
        getContext().getContentResolver().notifyChange(parameter.getUri(), null);
        return result;
    }

    @Override
    protected Class<DatabaseHelper> getHelperClass() {
        return DatabaseHelper.class;
    }
}
