package nl.antifraude.mijnid;

import android.content.Context;
import android.content.SharedPreferences;

public class CurrentUser {

    private String secretKey;
    private String bsn;

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("current_user", Context.MODE_PRIVATE);
    }

    public static CurrentUser fromPreferences(Context context) {
        CurrentUser instance = new CurrentUser();
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String key = sharedPreferences.getString("secretKey", null);
        if (key == null) {
            return null;
        }
        String bsn = sharedPreferences.getString("bsn", null);
        instance.secretKey = key;
        instance.bsn = bsn;
        return instance;
    }

    public static void clear(Context context) {
        getSharedPreferences(context).edit().clear().commit();
    }

    public static CurrentUser createUser(Context context, String secretKey, String bsn) {
        CurrentUser instance = new CurrentUser();
        instance.secretKey = secretKey;
        instance.bsn = bsn;
        instance.writeToPreferences(context);
        return instance;
    }

    public void writeToPreferences(Context context) {
        getSharedPreferences(context).edit()
                .putString("secretKey", secretKey)
                .putString("bsn", bsn)
                .apply();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBsn() {
        return bsn;
    }
}
