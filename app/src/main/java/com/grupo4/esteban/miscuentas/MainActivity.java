package com.grupo4.esteban.miscuentas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// Comprobad si la actividad ya ha sido creada con anterioridad
        if (savedInstanceState == null) {
// Crear un fragment
            MainFragment fragment = new MainFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }
}
