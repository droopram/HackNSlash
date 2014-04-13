package nl.antifraude.mijnid.app;

import android.content.Context;
import android.database.Cursor;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import nl.antifraude.mijnid.R;
import nl.antifraude.mijnid.model.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jozua on 2014/04/12.
 */
public class TimelineAdapter extends ResourceCursorAdapter {


    private static final Map<String, Integer> viewTypePerEventType = new HashMap<String, Integer>();

    static {
        viewTypePerEventType.put(Event.TYPE_LETTER, 0);
        viewTypePerEventType.put(Event.TYPE_KVK, 1);
        viewTypePerEventType.put(Event.TYPE_PASSPORT, 2);
        viewTypePerEventType.put(Event.TYPE_NAW_CHANGE, 3);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        Cursor cursor = (Cursor) getItem(position);
        Event event = Event.fromCursor(cursor);
        return viewTypePerEventType.get(event.getEventType());
    }

    public TimelineAdapter(Context context, int layout) {
        super(context, layout, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        if (getItemViewType(cursor.getPosition()) == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.timeline_item_letter, parent, false);
            view.setTag(view.findViewById(R.id.text));
            return view;
        } else if (getItemViewType(cursor.getPosition()) == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.timeline_item_kvk, parent, false);
            view.setTag(view.findViewById(R.id.text));
            return view;
        } else if (getItemViewType(cursor.getPosition()) == 2) {
            View view = LayoutInflater.from(context).inflate(R.layout.timeline_item_passport, parent, false);
            view.setTag(view.findViewById(R.id.text));
            return view;
        } else if (getItemViewType(cursor.getPosition()) == 3) {
            View view = LayoutInflater.from(context).inflate(R.layout.timeline_item_naw_change, parent, false);
            view.setTag(view.findViewById(R.id.text));
            return view;
        }

        return super.newView(context, cursor, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Event event = Event.fromCursor(cursor);
        TextView textView = (TextView) view.getTag();

        String title = event.getShortDescription();
        String body = event.getDescription();
        Spannable spannable = new SpannableString(title + "\n\n" + body);
        spannable.setSpan(new StyleSpan(R.style.TimelineItemText_Bold), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new StyleSpan(R.style.TimelineItemText), title.length() + 2, spannable.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannable);
    }
}
