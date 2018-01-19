package com.grupo4.esteban.miscuentas;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import java.util.Locale;

/**
 * Created by Esteban on 19/01/2018.
 */

public class ExpensesWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            Double result = 0.0;

            String selection = "kind = ?"; //Se define la sentencia SQL, donde se selecciona la columna kind de la BD.
            String[] selectionArgs = new String[]{context.getResources().getString(R.string.spend)}; //Se seleccionan los registros en los cuales coincida el tipo gasto o spend dependiendo del idioma.
            Cursor c = context.getContentResolver().query(MyAccountsContract.CONTENT_URI, null, selection, selectionArgs, MyAccountsContract.DEFAULT_SORT);//Se crea un objeto Cursor que devuela los registros que cumplen la consulta.

            if (c != null) { //Si el Cusor no es nulo porque devuelve algun registro de la consulta, se llevan a cabo las siguientes acciones
                c.moveToFirst(); //Nos movemos al primer registro.
                Double aux = c.getDouble(c.getColumnIndex("value")); //Se recoge el dato de la columna valor de dicho registro en una variable auxiliar.
                result = result + aux; //Se suma a la variable result el valor de la variable aux.
                while (c.moveToNext()) { //Se crea un bucle while que va pasando al siguiente registro del Cursor hasta que no queden más registros.
                    aux = c.getDouble(c.getColumnIndex("value")); //Se recoge el dato de la columna valor de dicho registro en una variable auxiliar.
                    result = result + aux; //Se suma a la variable result el valor de la variable aux.
                }
            }
            String simbolo = "$";
            if (Locale.getDefault().getLanguage() == "es")
                simbolo = "€";

            String number = context.getResources().getString(R.string.allExpenses) + result.toString() + simbolo;

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.expenses_widget);
            remoteViews.setTextViewText(R.id.textView, number);

            Intent intent = new Intent(context, ExpensesWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);


        }

    }
}
