package com.grupo4.esteban.miscuentas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Comprueba si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
            // Crea el fragment
            MyAccountsFragment fragment = new MyAccountsFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_main2, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

}
