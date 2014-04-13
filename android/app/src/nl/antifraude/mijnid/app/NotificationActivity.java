package nl.antifraude.mijnid.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import nl.antifraude.mijnid.R;

/**
 */
public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView titleView = (TextView) findViewById(R.id.title);
        TextView messageView = (TextView) findViewById(R.id.message);
        TextView timestampView = (TextView) findViewById(R.id.timestamp);
        Button buttonView = (Button) findViewById(R.id.button);

        titleView.setText(getIntent().getStringExtra("title"));
        messageView.setText(getIntent().getStringExtra("message"));
        timestampView.setText(getIntent().getStringExtra("timestamp"));

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHelpMessage();
            }
        });
    }

    private void showHelpMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Coming soon!")
                .setMessage("Deze functie is nog niet beschikbaar!")
                .show();
    }
}
