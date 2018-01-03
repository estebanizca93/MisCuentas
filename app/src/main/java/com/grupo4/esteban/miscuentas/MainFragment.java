package com.grupo4.esteban.miscuentas;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    Button buttonMyAccounts;
    Button buttonSpend;
    Button buttonDeposit;
    SharedPreferences prefs;

    //Se crea la vista inflando el fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Vistas
        buttonMyAccounts = (Button) view.findViewById(R.id.ButtonMyAccounts);
        buttonSpend = (Button) view.findViewById(R.id.ButtonExpenses);
        buttonDeposit = (Button) view.findViewById(R.id.ButtonDeposits);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        buttonMyAccounts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { //Acciones que se llevan a cabo al pulsar el botón.
                Intent MyAccounts = new Intent(v.getContext(), MyAccountsActivity.class);
                startActivityForResult(MyAccounts, 0);
            }
        });
        buttonSpend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { //Acciones que se llevan a cabo al pulsar el botón.
                Intent Spend = new Intent(v.getContext(), ExpensesActivity.class);
                startActivityForResult(Spend, 0);
            }
        });
        buttonDeposit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { //Acciones que se llevan a cabo al pulsar el botón.
                Intent Deposit = new Intent(v.getContext(), DepositsActivity.class);
                startActivityForResult(Deposit, 0);
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClicked");
    }
}
