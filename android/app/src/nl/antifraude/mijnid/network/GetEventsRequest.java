package nl.antifraude.mijnid.network;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

public class GetEventsRequest extends JsonObjectRequest {

    private static final String URL = "http://www.hawk-software.com/mijnid/public/auth/get-events";

    public GetEventsRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(URL, null, listener, errorListener);
    }
}
