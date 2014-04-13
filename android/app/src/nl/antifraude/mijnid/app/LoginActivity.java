package nl.antifraude.mijnid.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import nl.antifraude.mijnid.CurrentUser;
import nl.antifraude.mijnid.Installation;
import nl.antifraude.mijnid.R;
import nl.antifraude.mijnid.network.LoginRequest;
import nl.antifraude.mijnid.network.Network;
import nl.antifraude.mijnid.view.AsteriskTransformationMethod;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText pinView1;
    private EditText pinView2;
    private EditText pinView3;
    private EditText pinView4;
    private EditText pinView5;
    private GoogleCloudMessaging gcm;

    private static class PinWatcher implements TextWatcher {
        private final EditText next;
        private final EditText previous;

        public PinWatcher(EditText previous, EditText next) {
            this.next = next;
            this.previous = previous;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 1) {
                if (next != null) {
                    next.requestFocus();
                }
            } else {
                if (previous != null) {
                    previous.requestFocus();
                }
            }
        }
    }

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
        pinView1 = (EditText) findViewById(R.id.pin1);
        pinView2 = (EditText) findViewById(R.id.pin2);
        pinView3 = (EditText) findViewById(R.id.pin3);
        pinView4 = (EditText) findViewById(R.id.pin4);
        pinView5 = (EditText) findViewById(R.id.pin5);

        AsteriskTransformationMethod method = new AsteriskTransformationMethod();
        pinView1.setTransformationMethod(method);
        pinView2.setTransformationMethod(method);
        pinView3.setTransformationMethod(method);
        pinView4.setTransformationMethod(method);
        pinView5.setTransformationMethod(method);

        pinView1.addTextChangedListener(new PinWatcher(null, pinView2));
        pinView2.addTextChangedListener(new PinWatcher(pinView1, pinView3));
        pinView3.addTextChangedListener(new PinWatcher(pinView2, pinView4));
        pinView4.addTextChangedListener(new PinWatcher(pinView3, pinView5));
        pinView5.addTextChangedListener(new PinWatcher(pinView4, null));

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
        String pin1 = pinView1.getText().toString();
        String pin2 = pinView2.getText().toString();
        String pin3 = pinView3.getText().toString();
        String pin4 = pinView4.getText().toString();
        String pin5 = pinView5.getText().toString();
        String pin = pin1 + pin2 + pin3 + pin4 + pin5;
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
        finish();
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
