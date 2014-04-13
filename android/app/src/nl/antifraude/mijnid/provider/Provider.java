package nl.antifraude.mijnid.provider;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.tojc.ormlite.android.OrmLiteSimpleContentProvider;
import com.tojc.ormlite.android.framework.MatcherController;
import com.tojc.ormlite.android.framework.MatcherPattern;
import com.tojc.ormlite.android.framework.MimeTypeVnd;
import com.tojc.ormlite.android.framework.OperationParameters;
import nl.antifraude.mijnid.model.*;

/**
 */
public class Provider extends OrmLiteSimpleContentProvider<DatabaseHelper> {

    private static final int URI_CODE_EVENT_MANY = 1;
    private static final int URI_CODE_EVENT_ONE = 2;
    private static final int URI_CODE_USER_MANY = 3;
    private static final int URI_CODE_USER_ONE = 4;
    private static final int URI_CODE_BRIEF_MANY = 5;
    private static final int URI_CODE_BRIEF_ONE = 6;
    private static final int URI_CODE_INSTANTIE_MANY = 7;
    private static final int URI_CODE_INSTANTIE_ONE = 8;
    private static final int URI_CODE_PASSPORT_EVENT_MANY = 9;
    private static final int URI_CODE_PASSPORT_EVENT_ONE = 10;


    @Override
    public boolean onCreate() {
        setMatcherController(new MatcherController()
                        .add(Event.class, MimeTypeVnd.SubType.DIRECTORY, "", URI_CODE_EVENT_MANY)
                        .add(Event.class, MimeTypeVnd.SubType.ITEM, "#", URI_CODE_EVENT_ONE)
                        .add(User.class, MimeTypeVnd.SubType.DIRECTORY, "", URI_CODE_USER_MANY)
                        .add(User.class, MimeTypeVnd.SubType.ITEM, "#", URI_CODE_USER_ONE)
                        .add(Brief.class, MimeTypeVnd.SubType.DIRECTORY, "", URI_CODE_BRIEF_MANY)
                        .add(Brief.class, MimeTypeVnd.SubType.ITEM, "#", URI_CODE_BRIEF_ONE)
                        .add(PaspoortEvent.class, MimeTypeVnd.SubType.DIRECTORY, "", URI_CODE_PASSPORT_EVENT_MANY)
                        .add(PaspoortEvent.class, MimeTypeVnd.SubType.ITEM, "#", URI_CODE_PASSPORT_EVENT_ONE)
                        .add(Instantie.class, MimeTypeVnd.SubType.DIRECTORY, "", URI_CODE_INSTANTIE_MANY)
                        .add(Instantie.class, MimeTypeVnd.SubType.ITEM, "#", URI_CODE_INSTANTIE_ONE)
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
