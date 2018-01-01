package com.grupo4.esteban.miscuentas;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:{
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }


            case R.id.action_expenses:{
                new CalculateExpenses().execute();
                return true;
            }


            case R.id.action_login:{
                return true;
            }


            case R.id.action_purge: {
                int rows = getContentResolver().delete(MyAccountsContract.CONTENT_URI, null, null);
                Toast.makeText(this, rows + " filas de la base de datos borradas", Toast.LENGTH_LONG).show();
                return true;
            }
            default:
                return false;
        }
    }
    public double getAllExpenses(){
        String selection = "kind = ?";
        String [] selectionArgs = new String[]{"spend"};
        Double result = 0.0;
        Cursor c = getContentResolver().query(MyAccountsContract.CONTENT_URI,null,selection ,selectionArgs,MyAccountsContract.DEFAULT_SORT);
        if (c != null){
            c.moveToFirst();
            Double aux = c.getDouble(c.getColumnIndex("value"));
            Log.d(TAG, "AUX" + aux.toString());
            result = result + aux;
            while(c.moveToNext()) {
                aux = c.getDouble(c.getColumnIndex("value"));
                Log.d(TAG, "AUX" + aux.toString());
                result = result + aux;
            }

        }
        Log.d(TAG, "TOTAL: " + result);

        return result;
    }

    // Calcular todos loas gastos desde instalación de la APP de manera asincrona

    private final class CalculateExpenses extends AsyncTask<String, Void, String> {
        private double expenses;
        View parentLayout = findViewById(android.R.id.content);
        ProgressDialog progress = new ProgressDialog(MainActivity.this);

        public void onPreExecute() {
            progress.show();
//aquí se puede colocar código a ejecutarse previo
//a la operación
        }

        // Llamada al empezar
        @Override
        protected String doInBackground(String... params) {
            try {

                expenses = getAllExpenses();
                return "GASTOS TOTALES: " + String.format("%.2f",expenses) + " €";
            }
            catch (Exception e){
                return "ERROR: Calculo Gastos Totales";
            }

        }

        // Llamada cuando la actividad en background ha terminad!
        @Override
        protected void onPostExecute(String result) {

// Accion al completar la actualizacion del estado
            progress.dismiss();
            super.onPostExecute(result);
            Snackbar.make(parentLayout, result,Snackbar.LENGTH_LONG).show();
        }
    }
}

