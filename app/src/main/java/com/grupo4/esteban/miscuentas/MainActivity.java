package com.grupo4.esteban.miscuentas;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se crean las variables de la base de datos
        DbHelper dbHelper;
        SQLiteDatabase db;

        // Comprueba si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Crear el fragment
            MainFragment fragment = new MainFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
        //Se crea la Base de Datos
        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Se cierra la Base de Datos
        db.close();
    }

    @Override
    //Función que infla el menú.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    //Función que indica que hacer en cada caso al pulsar los diferentes botones del menú.
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:{ //Se lanza la activity Settings
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }


            case R.id.action_expenses:{ //Se crea el objeto CalculateExpenses que ejecuta una AsynTask que calcula el total de los gastos.
                new CalculateExpenses().execute();
                return true;
            }


            case R.id.action_login:{
                return true;
            }


            case R.id.action_purge: { //Se llama al ContentResolver del ContentProvider que ejecuta la función delete, eliminando todos los registros de la BD.
                int rows = getContentResolver().delete(MyAccountsContract.CONTENT_URI, null, null);
                //Se muestra un mensaje por pantlla de éxito de eliminación de los registros de la BD.
                Toast.makeText(this, rows + " " + getResources().getString(R.string.dbErase), Toast.LENGTH_LONG).show();
                return true;
            }
            default:
                return false;
        }
    }

    //Método que calcula los gastos totales de todos los registros de la base datos y los devuelve una variable de tipo Double.
    public double getAllExpenses(){
        Double result = 0.0; //Se inicializa la variable
        String selection = "kind = ?"; //Se define la sentencia SQL, donde se selecciona la columna kind de la BD.
        String [] selectionArgs = new String[]{getResources().getString(R.string.spend)}; //Se seleccionan los registros en los cuales coincida el tipo gasto o spend dependiendo del idioma.
        Cursor c = getContentResolver().query(MyAccountsContract.CONTENT_URI,null,selection ,selectionArgs,MyAccountsContract.DEFAULT_SORT);//Se crea un objeto Cursor que devuela los registros que cumplen la consulta.

        if (c != null){ //Si el Cusor no es nulo porque devuelve algun registro de la consulta, se llevan a cabo las siguientes acciones
            c.moveToFirst(); //Nos movemos al primer registro.
            Double aux = c.getDouble(c.getColumnIndex("value")); //Se recoge el dato de la columna valor de dicho registro en una variable auxiliar.
            result = result + aux; //Se suma a la variable result el valor de la variable aux.
            while(c.moveToNext()) { //Se crea un bucle while que va pasando al siguiente registro del Cursor hasta que no queden más registros.
                aux = c.getDouble(c.getColumnIndex("value")); //Se recoge el dato de la columna valor de dicho registro en una variable auxiliar.
                result = result + aux; //Se suma a la variable result el valor de la variable aux.
            }
        }

        return result;
    }


    // Objeto que calcula todos loos gastos desde instalación de la APP de manera asíncrona.
    private final class CalculateExpenses extends AsyncTask<String, Void, String> {
        private double expenses;
        View parentLayout = findViewById(android.R.id.content);
        ProgressDialog progress = new ProgressDialog(MainActivity.this);

        public void onPreExecute() { //Función que ejecuta acciones antes de comenzar la ejecución.
            progress.show();
        }

        // Llamada al empezar la ejecución.
        @Override
        protected String doInBackground(String... params) {
            try { //Se ejecutan las acciones para calcular los gastos totales
                expenses = getAllExpenses();
                // Se devuelve  un String con el cálculo total de los gastos
                return getResources().getString(R.string.allExpenses) + " " + String.format("%.2f",expenses) + "€";
            }
            catch (Exception e){ //Si falla la ejecución del cáclulo de los gastos totales, se devuelve un String con el error.
                return getResources().getString(R.string.errorAllExpenses);
            }

        }

        // Llamada cuando la actividad en background ha terminado
        @Override
        protected void onPostExecute(String result) {
        // Accion al completar el cálculo de los gastos
            progress.dismiss();
            super.onPostExecute(result); //Se recoge la varibale result con el String devuelto por la función doInBackground.
            Snackbar.make(parentLayout, result,Snackbar.LENGTH_LONG).show(); //Se muestra un mensaje de tipo Snackbar con el String de result.
        }
    }
}

