package nl.antifraude.mijnid.model;

import android.content.Context;
import android.content.SharedPreferences;

public class CurrentUser {

    private String secretKey;

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
        instance.secretKey = key;
        return instance;
    }

    public static CurrentUser createUser(Context context, String secretKey) {
        CurrentUser instance = new CurrentUser();
        instance.secretKey = secretKey;
        instance.writeToPreferences(context);
        return instance;
    }

    public void writeToPreferences(Context context) {
        getSharedPreferences(context).edit()
                .putString("secretKey", secretKey)
                .apply();
    }

    public String getSecretKey() {
        return secretKey;
    }
}
