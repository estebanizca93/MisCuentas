package com.grupo4.esteban.miscuentas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by Esteban on 30/12/2017.
 */

public class DepositsFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "DepositsActivity";
    Button buttonRegister;
    EditText txtConcept;
    EditText numValue;

    //Se crea la vista inflando el fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposits, container, false);

        //Vistas
        txtConcept = (EditText)view.findViewById(R.id.editTextConcept);
        numValue = (EditText)view.findViewById(R.id.editTextValue);
        buttonRegister = (Button) view.findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { //Acciones que se llevan a cabo al pulsar el botón.
                //Se recogen en variables los datos concepto y valor introducidos en la vista
                String concept = txtConcept.getText().toString();
                String value = numValue.getText().toString();
                //Sentencia IF que comprueba que se rellenan todos los campos
                if (TextUtils.isEmpty(concept) || TextUtils.isEmpty(value))
                    Snackbar.make(DepositsFragment.this.getView(), getResources().getString(R.string.fieldsEmpty), Snackbar.LENGTH_LONG).show();
                else {
                    //Sentencia IF que comprueba que la cantidad está en formato correcto
                    if (!Character.isDigit(value.charAt(0)))
                        Snackbar.make(DepositsFragment.this.getView(), getResources().getString(R.string.valueError), Snackbar.LENGTH_LONG).show();
                    //Si todas las comprobaciones son correctas, se ejecutan las siguientes funciones
                    else {
                        //Funciones y variables para formatear el valor a dos decimales.
                        double numValueDouble = Double.parseDouble(value);
                        DecimalFormatSymbols separadoresPersonalizados = new DecimalFormatSymbols();
                        separadoresPersonalizados.setDecimalSeparator('.');
                        DecimalFormat dosdecimales = new DecimalFormat("#.##", separadoresPersonalizados);
                        numValueDouble = Double.parseDouble(dosdecimales.format(numValueDouble));

                        //Se llaman a las funciones Set del Activity para que después sean insertados los datos en la BBDD
                        ((DepositsActivity) getActivity()).setTxtConcept(concept);
                        ((DepositsActivity) getActivity()).setNumValue(numValueDouble);
                        //Una vez "Seteados los datos" se llama a la función insertRegister del activity que insertará de forma correcta los datos en la BBDD.
                        ((DepositsActivity) getActivity()).insertRegister();
                        //Se muestra un mensaje de éxito si el registro se ha llevado a cabo correctamente
                        Toast.makeText(DepositsFragment.this.getActivity(), getResources().getString(R.string.succesDeposit), Toast.LENGTH_LONG).show();
                        //Se lanza automáticamente de nuevo el activity al menú principal de la aplicación, que en este caso es MainActivity.
                        Intent MainActivity = new Intent(v.getContext(), MainActivity.class);
                        startActivityForResult(MainActivity, 0);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClicked");
    }
}
