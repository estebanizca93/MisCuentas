package com.grupo4.esteban.miscuentas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Comprueba si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Crea el fragment
            MyAccountsFragment fragment = new MyAccountsFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

}
