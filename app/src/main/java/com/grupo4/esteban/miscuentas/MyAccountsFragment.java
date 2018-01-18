package com.grupo4.esteban.miscuentas;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = MyAccountsFragment.class.getSimpleName();
    private static final String[] FROM = {MyAccountsContract.Column.CONCEPT, MyAccountsContract.Column.KIND, MyAccountsContract.Column.VALUE, MyAccountsContract.Column.CREATED_AT};
    private static final int[] TO = {R.id.list_item_text_concept, R.id.list_item_text_kind, R.id.list_item_text_value, R.id.list_item_text_created_at};
    private SimpleCursorAdapter mAdapter;
    private static final int LOADER_ID = 42;


    //Método que se ejecuta cuando se crea la activity.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText("Sin datos..."); //Mensaje que se muestra si no hay datos en la base de datos.
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_myaccounts, null, FROM, TO, 0);

        //TextView mTextView = (TextView) getActivity().findViewById(R.id.list_item_text_kind);
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        //onLayaoutFinish(, mTextView);
        mAdapter.setViewBinder(new MyAccountsViewBinder());
    }

    //Método que se ejecuta cuendo se crea el Loader. Devuelve un CursorLoader con todos los registros de la base de datos.
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id != LOADER_ID)
            return null;
        Log.d(TAG, "onCreateLoader");
        return new CursorLoader(getActivity(), MyAccountsContract.CONTENT_URI, null, null, null, MyAccountsContract.DEFAULT_SORT);
    }

    //Método que se ejecuta cuando se acaba la creación del Loader. Rebice el loader y un cursor.
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        Log.d(TAG, "onLoadFinished with cursor: " + data.getCount());
        mAdapter.swapCursor(data);
        //onLayaoutFinish(data);

    }

    //Método que pretende repintar la interfaz, poniendo el texto de "tipo" ingreso en color verde y el de gasto en rojo.(No funciona).
    public void onLayaoutFinish(Cursor data, TextView mTextView) {

        if (data != null) {
            data.moveToFirst();
            String aux = data.getString(data.getColumnIndex("kind"));
            for (int i = 0; i < data.getCount(); i++) {
                //TextView mTextView = (TextView) getActivity().findViewById(R.id.list_item_text_kind);
                Log.d(TAG, "TextViewID: " + mTextView);
                Log.d(TAG, "AUX " + aux);
                if (aux.equals("spend"))
                    mTextView.setTextColor(Color.RED);
                else
                    mTextView.setTextColor(Color.GREEN);
                while (data.moveToNext()) {
                    aux = data.getString(data.getColumnIndex("kind"));
                    Log.d(TAG, "AUX" + aux);
                    if (aux.equals("spend"))
                        mTextView.setTextColor(Color.RED);
                    else
                        mTextView.setTextColor(Color.GREEN);
                }
            }
        }
        else
            Log.d(TAG, "DATA VACIA");
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

