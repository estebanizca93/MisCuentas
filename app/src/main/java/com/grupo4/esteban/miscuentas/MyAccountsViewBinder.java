package com.grupo4.esteban.miscuentas;

/**
 * Created by Esteban on 17/01/2018.
 */

import android.database.Cursor;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Locale;

import static android.content.ContentValues.TAG;

public class MyAccountsViewBinder implements SimpleCursorAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() == R.id.list_item_text_kind) {
            ((TextView) view).setText(cursor.getString(columnIndex));
            if ((cursor.getString(columnIndex).equals("Spend")) || (cursor.getString(columnIndex).equals("Gasto")))
                ((TextView) view).setTextColor(Color.RED);
            else
                ((TextView) view).setTextColor(Color.GREEN);
            return true;
        }
        if (view.getId() == R.id.list_item_text_value) {
            if (Locale.getDefault().getLanguage() == "es")
                ((TextView) view).setText(cursor.getString(columnIndex) + "€");
            else
                ((TextView) view).setText(cursor.getString(columnIndex) + "$");
            return true;
        } else
            return false;
    }
}
