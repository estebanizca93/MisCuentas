package com.grupo4.esteban.miscuentas;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Se crean las variables de la base de datos
        DbHelper dbHelper;
        SQLiteDatabase db;

        //Funciones para "dibujar" la barra de herramientas y la barra principal de la APP
        setContentView(R.layout.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Funciones para implementar la barra de navegación lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Comprueba si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Crear el fragment
            MainFragment fragment = new MainFragment();
            getFragmentManager().beginTransaction().add(R.id.content_main2, fragment, fragment.getClass().getSimpleName()).commit();
        }
        //Se crea la Base de Datos
        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //Se cierra la Base de Datos
        db.close();
    }


    //Función que captura la apertura y cierre de la barra de navegación lateral.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflae el menú.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    //Función que indica que hacer en cada caso al pulsar los diferentes botones del menú.
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings: { //Se lanza la activity Settings
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            default:
                return false;
        }
    }

    //Método que calcula los gastos totales de todos los registros de la base datos y los devuelve una variable de tipo Double.
    public double getAllExpenses() {
        Double result = 0.0; //Se inicializa la variable
        String selection = "kind = ?"; //Se define la sentencia SQL, donde se selecciona la columna kind de la BD.
        String[] selectionArgs = new String[]{getResources().getString(R.string.spend)}; //Se seleccionan los registros en los cuales coincida el tipo gasto o spend dependiendo del idioma.
        Cursor c = getContentResolver().query(MyAccountsContract.CONTENT_URI, null, selection, selectionArgs, MyAccountsContract.DEFAULT_SORT);//Se crea un objeto Cursor que devuela los registros que cumplen la consulta.

        if (c != null) { //Si el Cusor no es nulo porque devuelve algun registro de la consulta, se llevan a cabo las siguientes acciones
            c.moveToFirst(); //Nos movemos al primer registro.
            Double aux = c.getDouble(c.getColumnIndex("value")); //Se recoge el dato de la columna valor de dicho registro en una variable auxiliar.
            result = result + aux; //Se suma a la variable result el valor de la variable aux.
            while (c.moveToNext()) { //Se crea un bucle while que va pasando al siguiente registro del Cursor hasta que no queden más registros.
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
                return getResources().getString(R.string.allExpenses) + " " + String.format("%.2f", expenses) + "€";
            } catch (Exception e) { //Si falla la ejecución del cáclulo de los gastos totales, se devuelve un String con el error.
                return getResources().getString(R.string.errorAllExpenses);
            }

        }

        // Llamada cuando la actividad en background ha terminado
        @Override
        protected void onPostExecute(String result) {
            // Accion al completar el cálculo de los gastos
            progress.dismiss();
            super.onPostExecute(result); //Se recoge la varibale result con el String devuelto por la función doInBackground.
            //Implementación del cuadro de diálogo que se mostrará los gastos totales cuando se termine la ejeucción del cálculo.
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
            }
            builder.setTitle(getResources().getString(R.string.titleAllExpenses))
                    .setMessage(result)
                    .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
        }
    }

    //Implementación de la función encargada de capturar las opciones de la barra de navegación lateral.
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_allexpenses) {
            //Se ejecuta el Asyntask CalculateExpenses que calculará los gastos totales acumulados.
            new MainActivity.CalculateExpenses().execute();
        } else if (id == R.id.nav_purge) {
            //Se muestra un cuadro de diálogo para confirmar la eliminación de la BBDD
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(MainActivity.this);
            }
            builder.setTitle(getResources().getString(R.string.dbEraseConfirmTitle))
                    .setMessage(getResources().getString(R.string.dbEraseConfirmMSG))
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int rows = getContentResolver().delete(MyAccountsContract.CONTENT_URI, null, null);
                            Toast.makeText(MainActivity.this, rows + " " + getResources().getString(R.string.dbErase), Toast.LENGTH_LONG).show();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
