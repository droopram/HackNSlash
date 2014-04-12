package nl.antifraude.mijnid.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import nl.antifraude.mijnid.R;
import nl.antifraude.mijnid.network.LoginRequest;
import nl.antifraude.mijnid.network.Network;
import org.json.JSONException;
import org.json.JSONObject;

/**
 */
public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        Toast.makeText(LoginActivity.this, "001 - Login mislukt!", Toast.LENGTH_LONG).show();
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
            Toast.makeText(this, "002 - Login mislukt!", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Could not parse JSON response from SERVER");
        }
    }

    private void nextActivity() {
        Intent intent = new Intent(this, TimelineActivity.class);
        startActivity(intent);
    }
}
