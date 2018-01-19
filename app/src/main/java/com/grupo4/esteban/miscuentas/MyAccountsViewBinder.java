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
    //Método que "repinta" la vista.
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        if (view.getId() == R.id.list_item_text_kind) { //Sentencia if que se ejecuta si el objeto de la vista es el de tipo.
            ((TextView) view).setText(cursor.getString(columnIndex));//Se pinta los datos de los valores de la BD.
            if ((cursor.getString(columnIndex).equals("Spend")) || (cursor.getString(columnIndex).equals("Gasto")))
                ((TextView) view).setTextColor(Color.RED);//Si es tipo gasto se pinta en rojo.
            else
                ((TextView) view).setTextColor(Color.GREEN);//Si no es tipo gasto(ingreso) se pinta en verde.
            return true;
        }
        if (view.getId() == R.id.list_item_text_value) {//Sentencia if que se ejecuta si el objeto de la vista es el de valor.
            if (Locale.getDefault().getLanguage() == "es")
                ((TextView) view).setText(cursor.getString(columnIndex) + "€");//Se pinta un símbolo de euro si el idioma es españo.
            else
                ((TextView) view).setText(cursor.getString(columnIndex) + "$");//Se pinta un símbolo de dolar si el idioma no es español.
            return true;
        } else
            return false;
    }
}
