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
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Esteban on 29/12/2017.
 */

public class ExpensesFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ExpensesActivity";
    Button buttonRegister;
    EditText txtConcept;
    EditText numValue;
    SharedPreferences prefs;

    //Se crea la vista inflando el fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expenses, container, false);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Vistas
        txtConcept = (EditText) view.findViewById(R.id.editTextCocept);
        numValue = (EditText) view.findViewById(R.id.editTextValue);
        buttonRegister = (Button) view.findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { //Acciones que se llevan a cabo al pulsar el botón.
                //Se recogen en variables los datos concepto y valor introducidos en la vista
                String concept = txtConcept.getText().toString();
                String value = numValue.getText().toString();
                double numValueDouble = Double.parseDouble(value);
                float spendLimit = prefs.getFloat("decimal", 10000);

                //Funciones

                //Se llaman a las funciones Set del Activity para que después sean insertados los datos en la BBDD
                ((ExpensesActivity) getActivity()).setTxtConcept(concept);
                ((ExpensesActivity) getActivity()).setNumValue(numValueDouble);
                //Una vez "Seteados los datos" se llama a la función insertRegister del activity que insertará de forma correcta los datos en la BBDD.
                ((ExpensesActivity) getActivity()).insertRegister();
                //Se muestra un mensaje de éxito si el registro se ha llevado a cabo correctamente
                Toast.makeText(ExpensesFragment.this.getActivity(), getResources().getString(R.string.succesSpend), Toast.LENGTH_LONG).show();
                //Sentencia IF para mostrar
                if (((ExpensesActivity) getActivity()).getAllExpenses() > spendLimit)
                    Toast.makeText(ExpensesFragment.this.getActivity(), getResources().getString(R.string.limitExceeded), Toast.LENGTH_LONG).show();
                //Se lanza automáticamente de nuevo el activity al menú principal de la aplicación, que en este caso es MainActivity.
                Intent MainActivity = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(MainActivity, 0);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClicked");
    }

}


