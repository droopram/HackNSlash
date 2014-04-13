package nl.antifraude.mijnid.network;

import android.content.Context;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import nl.antifraude.mijnid.CurrentUser;
import org.json.JSONException;
import org.json.JSONObject;

public class GetDataRequest extends JsonObjectRequest {

    private static final String URL = "http://www.hawk-software.com/mijnid/public/me/get-data";

    public GetDataRequest(Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(URL, wrapSecretKey(context), listener, errorListener);
    }

    private static JSONObject wrapSecretKey(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("skey", CurrentUser.fromPreferences(context).getSecretKey());
            return jsonObject;
        } catch (JSONException e) {
            throw new Error(e);
        }
    }
}
