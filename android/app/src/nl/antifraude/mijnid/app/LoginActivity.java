package nl.antifraude.mijnid.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import nl.antifraude.mijnid.Installation;
import nl.antifraude.mijnid.R;
import nl.antifraude.mijnid.network.LoginRequest;
import nl.antifraude.mijnid.network.Network;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText pinView;
    private GoogleCloudMessaging gcm;

    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    private String SENDER_ID = "275929162533";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Installation.checkPlayServices(this)) {
            Toast.makeText(this, getString(R.string.play_services_not_installed_toast), Toast.LENGTH_LONG).show();
        }

        gcm = GoogleCloudMessaging.getInstance(this);
        String registrationId = Installation.getRegistrationId(this);

        if (registrationId.isEmpty()) {
            registerInBackground();
        }

        if (CurrentUser.fromPreferences(this) != null) {
            nextActivity();
        }

        setContentView(R.layout.activity_login);
        pinView = (EditText) findViewById(R.id.pin);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Network.getRequestQueue(this).cancelAll(this);
        super.onDestroy();
    }

    private void login() {
        String pin = pinView.getText().toString();
        Request loginRequest = new LoginRequest(this, pin,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onLoginSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onLoginFailed(error);
            }
        }
        ).setTag(this);
        Network.getRequestQueue(this).add(loginRequest);
    }

    private void onLoginFailed(VolleyError error) {
        Toast.makeText(LoginActivity.this, getString(R.string.login_failed_toast), Toast.LENGTH_LONG).show();
        Log.e(TAG, "Login failed, see stacktrace.", error);
        Log.e(TAG, "Responsecode: " + error.networkResponse.statusCode);
        Log.e(TAG, "Response: " + new String(error.networkResponse.data));
    }

    private void onLoginSuccess(JSONObject serverResponse) {
        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_LONG).show();
        Log.i(TAG, "Login successful. Response: " + serverResponse.toString());
        try {
            String secretKey = serverResponse.getString("skey");
            String bsn = serverResponse.getString("bsn");
            CurrentUser.createUser(this, secretKey, bsn);
            nextActivity();
        } catch (JSONException e) {
            Toast.makeText(this, getString(R.string.login_failed_parse_error_toast), Toast.LENGTH_LONG).show();
            Log.e(TAG, "Could not parse JSON response from SERVER");
        }
    }

    private void nextActivity() {
        Intent intent = new Intent(this, TimelineActivity.class);
        startActivity(intent);
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p/>
     * Stores the registration ID and app versionCode in the application's
     * shared preferences.
     */
    private void registerInBackground() {


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                String regid = "";
                Context context = LoginActivity.this;
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // Persist the regID - no need to register again.
                    Installation.storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                Log.i(TAG, "Registration message: " + msg);
            }
        }.execute(null, null, null);
    }
}
