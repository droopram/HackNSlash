package nl.antifraude.mijnid.network;

import android.content.Context;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class LoginRequest extends JsonObjectRequest {

    private static final String URL = "http://www.hawk-software.com/mijnid/public/auth/login";

    public LoginRequest(Context context, String pin,
                        Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(URL, wrapRequest(context, pin), listener, errorListener);
    }

    private static JSONObject wrapRequest(Context context, String pin) {
        JSONObject jsonObject = new JSONObject();
        try {
            // TODO
//            jsonObject.put("device_id", Installation.getAndroidId(context));
//            jsonObject.put("pin", pin);
            jsonObject.put("device_id", "xxxx");
            jsonObject.put("pin", "0000");
            return jsonObject;
        } catch (JSONException e) {
            throw new Error(e);
        }
    }
}
