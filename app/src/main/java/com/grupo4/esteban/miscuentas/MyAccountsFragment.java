package com.grupo4.esteban.miscuentas;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = MyAccountsFragment.class.getSimpleName();
    private static final String[] FROM = {MyAccountsContract.Column.CONCEPT, MyAccountsContract.Column.KIND, MyAccountsContract.Column.VALUE, MyAccountsContract.Column.CREATED_AT};
    private static final int[] TO = {R.id.list_item_text_concept, R.id.list_item_text_kind, R.id.list_item_text_value, R.id.list_item_text_created_at};
    private SimpleCursorAdapter mAdapter;

    private static final int LOADER_ID = 42;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null, FROM, TO, 0);
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id != LOADER_ID)
        return null;
                Log.d(TAG, "onCreateLoader");
        return new CursorLoader(getActivity(), MyAccountsContract.CONTENT_URI, null, null, null, MyAccountsContract.DEFAULT_SORT);
    }

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

