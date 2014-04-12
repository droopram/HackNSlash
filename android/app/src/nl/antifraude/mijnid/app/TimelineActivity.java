package nl.antifraude.mijnid.app;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import nl.antifraude.mijnid.R;
import nl.antifraude.mijnid.network.LogoutRequest;
import nl.antifraude.mijnid.network.Network;
import nl.antifraude.mijnid.provider.Contract;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class TimelineActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = TimelineActivity.class.getSimpleName();
    private TimelineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TimelineAdapter(this, R.layout.timeline_adapter_item);

        setContentView(R.layout.activity_timeline);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
    }

    private void logout() {
        Request logoutRequest = new LogoutRequest(this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onLogoutSuccessful(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLogoutFailed(error);
            }
        });
        Network.getRequestQueue(this).add(logoutRequest);
    }

    private void onLogoutSuccessful(JSONObject response) {
        try {
            Log.i(TAG, "Logout successful. Response: " + response.getString("message"));
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse json response", e);
        }
    }

    private void onLogoutFailed(VolleyError error) {
        Toast.makeText(this, "Uitloggen mislukt!", Toast.LENGTH_LONG).show();
        int responseCode = error.networkResponse.statusCode;
        String message = new String(error.networkResponse.data);
        Log.e(TAG, "Failed to logout. Responsecode: " + responseCode + ". Message: " + message);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contract.Event.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
