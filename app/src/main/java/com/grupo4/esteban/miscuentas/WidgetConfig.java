package com.grupo4.esteban.miscuentas;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Esteban on 19/01/2018.
 */

public class WidgetConfig extends Activity {

    private int widgetId = 0;

    private Button btnAceptar;
    private Button btnCancelar;
    private EditText txtMensaje;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_config);

        //Obtenemos el Intent que ha lanzado esta ventana
        //y recuperamos sus parámetros
        Intent intentOrigen = getIntent();
        Bundle params = intentOrigen.getExtras();

        //Obtenemos el ID del widget que se está configurando
        widgetId = params.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        //Establecemos el resultado por defecto (si se pulsa el botón 'Atrás'
        //del teléfono será éste el resultado devuelto).
        setResult(RESULT_CANCELED);

        //...
        //Obtenemos la referencia a los controles de la pantallabtn
        btnAceptar = (Button)findViewById(R.id.BtnAceptar);
        btnCancelar = (Button)findViewById(R.id.BtnCancelar);
        txtMensaje = (EditText)findViewById(R.id.TxtMensaje);

        //Implementación del botón "Cancelar"
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Devolvemos como resultado: CANCELAR (RESULT_CANCELED)
                finish();
            }
        });

        //Implementación del botón "Aceptar"
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guardamos el mensaje personalizado en las preferencias
                SharedPreferences prefs = getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("msg_" + widgetId, txtMensaje.getText().toString());
                editor.commit();

                //Actualizamos el widget tras la configuración
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetConfig.this);
                MiWidget.actualizarWidget(WidgetConfig.this, appWidgetManager, widgetId);

                //Devolvemos como resultado: ACEPTAR (RESULT_OK)
                Intent resultado = new Intent();
                resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
                setResult(RESULT_OK, resultado);
                finish();
            }
        });

    }

}
