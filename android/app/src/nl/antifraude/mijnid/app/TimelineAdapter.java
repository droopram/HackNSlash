package nl.antifraude.mijnid.app;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import nl.antifraude.mijnid.model.Event;

/**
 * Created by jozua on 2014/04/12.
 */
public class TimelineAdapter extends ResourceCursorAdapter {
    public TimelineAdapter(Context context, int layout) {
        super(context, layout, null, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Event event = Event.fromCursor(cursor);
        TextView textView = (TextView)view;
        textView.setText(event.getDescription());
    }
}
