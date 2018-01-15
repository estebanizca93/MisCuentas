package com.grupo4.esteban.miscuentas;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Esteban on 30/12/2017.
 */

public class DepositsActivity extends AppCompatActivity {

    public String txtConcept;
    public Double numValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Comprueba si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Se crea el Fragment
            DepositsFragment fragment = new DepositsFragment();
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
        values.put(MyAccountsContract.Column.KIND, getResources().getString(R.string.deposit));
        values.put(MyAccountsContract.Column.VALUE, numValue);
        values.put(MyAccountsContract.Column.CREATED_AT, fecha);
        Uri uri = getContentResolver().insert(MyAccountsContract.CONTENT_URI, values);
    }
}
