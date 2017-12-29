package com.grupo4.esteban.miscuentas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DbHelper dbHelper;
        SQLiteDatabase db;

// Comprobad si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
// Crear un fragment
            MainFragment fragment = new MainFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
        //Creación de la Base de Datos
        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Insertar en la base de datos
        /*values.clear();

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);
        values.put(MyAccountsContract.Column.CONCEPT, "concepto prueba");
        values.put(MyAccountsContract.Column.KIND, "tipo prueba");
        values.put(MyAccountsContract.Column.VALUE, 10000);
        values.put(MyAccountsContract.Column.CREATED_AT, fecha);
        Uri uri = getContentResolver().insert(MyAccountsContract.CONTENT_URI, values);*/


// Cerrar la base de datos
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_expenses:

                return true;
            case R.id.action_login:

                return true;
            case R.id.action_purge:
                int rows = getContentResolver().delete(MyAccountsContract.CONTENT_URI,null, null);
                    Toast.makeText(this, rows+" filas de la base de datos borradas", Toast.LENGTH_LONG).show();
                return true;
            default:
                return false;
        }
    }
}
