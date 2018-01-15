package com.grupo4.esteban.miscuentas;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        // Comprueba si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Se crea el Fragment
            ExpensesFragment fragment = new ExpensesFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    //Función que recibe un String con el concepto y rellena la variable local txtConcept con dicha información.
    protected void setTxtConcept(String concept) {
        txtConcept = concept;
    }

    //Función que recibe un Double con el valor y rellena la variable local numValue con dicha información.
    protected void setNumValue(Double value) {
        numValue = value;
    }

    //Función que inserta los datos en la base de datos.
    protected void insertRegister() {
        ContentValues values = new ContentValues();
        //Variables que formatean y devuelven la fecha actual en formato Hora:Minuto:Segundos Dia-Mes-Año
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);

        values.clear();
        values.put(MyAccountsContract.Column.CONCEPT, txtConcept);
        values.put(MyAccountsContract.Column.KIND, getResources().getString(R.string.spend));
        values.put(MyAccountsContract.Column.VALUE, numValue);
        values.put(MyAccountsContract.Column.CREATED_AT, fecha);
        Uri uri = getContentResolver().insert(MyAccountsContract.CONTENT_URI, values);
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
}
