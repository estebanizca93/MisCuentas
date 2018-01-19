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

        setEmptyText(getResources().getString(R.string.emptyList)); //Mensaje que se muestra si no hay datos en la base de datos.
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.fragment_myaccounts, null, FROM, TO, 0);
        setListAdapter(mAdapter);
        mAdapter.setViewBinder(new MyAccountsViewBinder());
        getLoaderManager().initLoader(LOADER_ID, null, this);

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

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

