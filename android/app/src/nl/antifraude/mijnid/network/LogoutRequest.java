package nl.antifraude.mijnid.network;

import android.content.Context;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import nl.antifraude.mijnid.app.CurrentUser;
import org.json.JSONException;
import org.json.JSONObject;

public class LogoutRequest extends JsonObjectRequest {

    private static final String URL = "http://www.hawk-software.com/mijnid/public/auth/login";

    public LogoutRequest(Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(URL, wrapRequest(context), listener, errorListener);
    }

    private static JSONObject wrapRequest(Context context) {
        CurrentUser currentUser = CurrentUser.fromPreferences(context);
        if (currentUser == null) {
            throw new IllegalStateException("Cannot logout if not currently logged in");
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("skey", currentUser.getSecretKey());
            return jsonObject;
        } catch (JSONException e) {
            throw new Error(e);
        }
    }
}
