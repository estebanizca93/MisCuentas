package com.grupo4.esteban.miscuentas;

import android.app.FragmentManager;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Esteban on 29/12/2017.
 */

public class ExpensesActivity extends AppCompatActivity {

    public String txtConcept;
    public Double numValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Check whether this activity was created before
        if (savedInstanceState == null) {
// Create a fragment
            ExpensesFragment fragment = new ExpensesFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    protected void setTxtConcept(String concept){
        txtConcept = concept;
    }

    protected void setNumValue(Double value){
        numValue = value;
    }

    protected void insertRegister(){

        // Insertar en la base de datos

        ContentValues values = new ContentValues();

        // Insertar en la base de datos
        values.clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        values.put(MyAccountsContract.Column.CONCEPT, txtConcept);
        values.put(MyAccountsContract.Column.KIND, "spend");
        values.put(MyAccountsContract.Column.VALUE, numValue);
        values.put(MyAccountsContract.Column.CREATED_AT, fecha);
        Uri uri = getContentResolver().insert(MyAccountsContract.CONTENT_URI, values);
    }
}
