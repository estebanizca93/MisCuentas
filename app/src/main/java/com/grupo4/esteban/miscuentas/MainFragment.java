package com.grupo4.esteban.miscuentas;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    Button buttonMyAccounts;
    Button buttonSpend;
    Button buttonDeposit;
    SharedPreferences prefs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Vistas
        buttonMyAccounts = (Button) view.findViewById(R.id.ButtonMyAccounts);
        buttonSpend = (Button) view.findViewById(R.id.ButtonExpenses);
        buttonDeposit = (Button) view.findViewById(R.id.ButtonDeposits);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        buttonMyAccounts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent MyAccounts = new Intent(v.getContext(), MyAccountsActivity.class);
                startActivityForResult(MyAccounts, 0);
            }
        });
        buttonSpend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent Spend = new Intent(v.getContext(), ExpensesActivity.class);
                startActivityForResult(Spend, 0);
            }
        });
        buttonDeposit.setOnClickListener(this);

        return view;
    }

    // Calcular todos loas gastos desde instalación de la APP de manera asincrona
    /*
    private final class PostTask extends AsyncTask<String, Void, String> {
// Llamada al empezar
        @Override
        protected String doInBackground(String... params) {
            try {
                    twitter.updateStatus(params[0]);
                return "Tweet enviado correctamente";
            } catch (TwitterException e) {
                    Log.e(TAG, "Fallo en el envío");
                    e.printStackTrace();
                return "Fallo en el envío del tweet";
            }
        }

// Llamada cuando la actividad en background ha terminad!
        @Override
        protected void onPostExecute(String result) {
// Accion al completar la actualizacion del estado
    super.onPostExecute(result);
    Toast.makeText(MainFragment.this.getActivity(), "GASTOS TOTALES: ", Toast.LENGTH_LONG).show();!
        }
    } */

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClicked");
    }
}
