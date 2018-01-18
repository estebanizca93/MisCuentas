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

public class MyAccountsViewBinder implements SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() != R.id.list_item_text_kind)
            return false;

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
