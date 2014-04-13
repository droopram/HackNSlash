package nl.antifraude.mijnid.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import nl.antifraude.mijnid.CurrentUser;
import nl.antifraude.mijnid.R;
import nl.antifraude.mijnid.model.Event;
import nl.antifraude.mijnid.model.User;
import nl.antifraude.mijnid.network.GetDataRequest;
import nl.antifraude.mijnid.network.GetEventsRequest;
import nl.antifraude.mijnid.network.LogoutRequest;
import nl.antifraude.mijnid.network.Network;
import nl.antifraude.mijnid.provider.Contract;
import org.json.JSONArray;
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

        downloadEvents();
        downloadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timeline_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Network.getRequestQueue(this).cancelAll(this);
        super.onDestroy();
    }

    private void downloadEvents() {
        RequestQueue queue = Network.getRequestQueue(this);
        Request getEventsRequest = new GetEventsRequest(this,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onDownloadEventsSuccessful(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onDownloadEventsFailed(error);
            }
        }
        );
        getEventsRequest.setTag(this);
        queue.add(getEventsRequest);
    }

    private void downloadData() {
        RequestQueue queue = Network.getRequestQueue(this);
        Request getDataRequest = new GetDataRequest(this,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onDownloadDataSuccessful(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onDownloadDataFailed(error);
            }
        }
        );
        getDataRequest.setTag(this);
        queue.add(getDataRequest);
    }

    private void onDownloadDataSuccessful(JSONObject response) {
        try {
            JSONObject message = response.getJSONObject("message");
            User user = User.fromJson(message.getJSONObject("user"));

            JSONArray lettersArray = message.getJSONArray("letters");
            ContentValues[] brievenContentValues = new ContentValues[lettersArray.length()];
            nl.antifraude.mijnid.model.Brief[] brieven = new nl.antifraude.mijnid.model.Brief[lettersArray.length()];
            for (int i = 0; i < lettersArray.length(); i++) {
                JSONObject jsonObject = lettersArray.getJSONObject(i);
                brieven[i] = nl.antifraude.mijnid.model.Brief.fromJson(jsonObject);
                brievenContentValues[i] = brieven[i].toContentValues();
            }

            JSONArray passportEventsArray = message.getJSONArray("passport_events");
            ContentValues[] paspoortEventContentValues = new ContentValues[passportEventsArray.length()];
            nl.antifraude.mijnid.model.PaspoortEvent[] paspoortEvents = new nl.antifraude.mijnid.model.PaspoortEvent[passportEventsArray.length()];
            for (int i = 0; i < passportEventsArray.length(); i++) {
                JSONObject jsonObject = passportEventsArray.getJSONObject(i);
                paspoortEvents[i] = nl.antifraude.mijnid.model.PaspoortEvent.fromJson(jsonObject);
                paspoortEventContentValues[i] = paspoortEvents[i].toContentValues();
            }

            // user
            getContentResolver().delete(Contract.User.CONTENT_URI, null, null);
            getContentResolver().insert(Contract.User.CONTENT_URI, user.toContentValues());

            // brieven
            getContentResolver().delete(Contract.Brief.CONTENT_URI, null, null);
            getContentResolver().bulkInsert(Contract.Brief.CONTENT_URI, brievenContentValues);

            // paspoort events
            getContentResolver().delete(Contract.PaspoortEvent.CONTENT_URI, null, null);
            getContentResolver().bulkInsert(Contract.PaspoortEvent.CONTENT_URI, paspoortEventContentValues);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void onDownloadDataFailed(VolleyError error) {
        new AlertDialog.Builder(this).setTitle("Error!").setMessage("Error received from GetData, see LogCat").show();
        int statusCode = error.networkResponse.statusCode;
        String message = new String(error.networkResponse.data);
        Log.e(TAG, "ResponseCode: " + statusCode + ". Message: " + message, error);
    }

    private void onDownloadEventsSuccessful(JSONObject response) {
        try {
            JSONArray message = response.getJSONArray("message");
            ContentValues[] events = new ContentValues[message.length()];
            for (int i = 0; i < message.length(); i++) {
                JSONObject object = message.getJSONObject(i);
                Event event = Event.fromJson(object);
                events[i] = event.toContentValues();
            }
            getContentResolver().delete(Contract.Event.CONTENT_URI, null, null);
            getContentResolver().bulkInsert(Contract.Event.CONTENT_URI, events);
        } catch (JSONException e) {
            new AlertDialog.Builder(this).setTitle("Error!").setMessage("Could not parse JSON from GetEvents").show();
        }
    }

    private void onDownloadEventsFailed(VolleyError error) {
        new AlertDialog.Builder(this).setTitle("Error!").setMessage("Error received from GetEvents, see LogCat").show();
        int statusCode = error.networkResponse.statusCode;
        String message = new String(error.networkResponse.data);
        Log.e(TAG, "ResponseCode: " + statusCode + ". Message: " + message, error);
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

        CurrentUser.clear(this);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
