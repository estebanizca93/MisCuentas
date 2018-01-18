package com.grupo4.esteban.miscuentas;

/**
 * Created by Esteban on 17/01/2018.
 */
import android.database.Cursor;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

class MyAccountsViewBinder implements SimpleCursorAdapter.ViewBinder {
    private static final String TAG = MyAccountsViewBinder.class.getSimpleName();
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() != R.id.list_item_text_kind)
            return false;

// Convertimos timestamp a tiempo relativo
        //long timestamp = cursor.getLong(columnIndex);
        //CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(timestamp);
       // ((TextView) view).setText(relativeTime);
//        Log.d(TAG, "VIEWBINDER: " + view.findViewById(R.id.list_item_text_kind).toString());
        else {
            ((TextView) view).setText(cursor.getString(columnIndex));
            if ((cursor.getString(columnIndex).equals("Spend")) || (cursor.getString(columnIndex).equals("Gasto")))

                ((TextView) view).setTextColor(Color.RED);
            else
                ((TextView) view).setTextColor(Color.GREEN);
            return true;
        }
    }
}
