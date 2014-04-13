package nl.antifraude.mijnid.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import nl.antifraude.mijnid.model.*;

import java.sql.SQLException;

/**
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "mijnid.sqlite";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Event.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Brief.class);
            TableUtils.createTableIfNotExists(connectionSource, PaspoortEvent.class);
            TableUtils.createTableIfNotExists(connectionSource, Instantie.class);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        // clear all data and setup new table versions
        try {
            TableUtils.dropTable(connectionSource, Event.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Brief.class, true);
            TableUtils.dropTable(connectionSource, PaspoortEvent.class, true);
            TableUtils.dropTable(connectionSource, Instantie.class, true);
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Brief.class);
            TableUtils.createTable(connectionSource, PaspoortEvent.class);
            TableUtils.createTable(connectionSource, Instantie.class);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

}
