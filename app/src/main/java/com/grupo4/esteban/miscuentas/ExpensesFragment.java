package com.grupo4.esteban.miscuentas;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Esteban on 29/12/2017.
 */

public class ExpensesFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "ExpensesActivity";
    Button buttonRegister;
    EditText txtConcept;
    EditText numValue;
    SharedPreferences prefs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);


        //Vistas
        txtConcept = (EditText)view.findViewById(R.id.editTextCocept);
        numValue = (EditText)view.findViewById(R.id.editTextValue);
        buttonRegister = (Button) view.findViewById(R.id.buttonRegister);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String concept = txtConcept.getText().toString();
                String value = numValue.getText().toString();
                double numValueDouble = Double.parseDouble(value);
                ((ExpensesActivity)getActivity()).setTxtConcept(concept);
                ((ExpensesActivity)getActivity()).setNumValue(numValueDouble);
                ((ExpensesActivity)getActivity()).insertRegister();
                Toast.makeText(ExpensesFragment.this.getActivity(), "Exito", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClicked");
    }

}


