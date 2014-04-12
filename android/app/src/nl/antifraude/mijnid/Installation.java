package nl.antifraude.mijnid;

import android.content.Context;
import android.provider.Settings;
import nl.antifraude.mijnid.app.CurrentUser;

/**
 */
public class Installation {

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getSecretKey(Context context) {
        return CurrentUser.fromPreferences(context).getSecretKey();
    }
}
