package com.grupo4.esteban.miscuentas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
            default:
                return false;
        }
    }
}
