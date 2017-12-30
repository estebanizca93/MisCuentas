package com.grupo4.esteban.miscuentas;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Esteban on 30/12/2017.
 */

public class DepositsFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "DepositsActivity";
    Button buttonRegister;
    EditText txtConcept;
    EditText numValue;
    SharedPreferences prefs;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposits, container, false);


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
                ((DepositsActivity)getActivity()).setTxtConcept(concept);
                ((DepositsActivity)getActivity()).setNumValue(numValueDouble);
                ((DepositsActivity)getActivity()).insertRegister();
                Toast.makeText(DepositsFragment.this.getActivity(), "Exito", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClicked");
    }
}
