package com.grupo4.esteban.miscuentas;

import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    TextView list_item_text_concept;
    TextView list_item_text_kind;
    TextView list_item_text_value;
    TextView list_item_text_created_at;
    TextView textViewValue;
    TextView textViewConcept;
    TextView textViewKind;
    TextView textViewCreatedAt;
    SharedPreferences prefs;
    private static final String TAG = MyAccountsFragment.class.getSimpleName();
    private static final String[] FROM = {MyAccountsContract.Column.CONCEPT, MyAccountsContract.Column.KIND, MyAccountsContract.Column.VALUE, MyAccountsContract.Column.CREATED_AT};
    private static final int[] TO = {R.id.list_item_text_concept, R.id.list_item_text_kind, R.id.list_item_text_value, R.id.list_item_text_created_at};
    private SimpleCursorAdapter mAdapter;

    private static final int LOADER_ID = 42;

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myaccounts, container, false);

        //Vistas
        list_item_text_concept = (TextView) view.findViewById(R.id.list_item_text_concept);
        list_item_text_kind = (TextView) view.findViewById(R.id.list_item_text_kind);
        list_item_text_value = (TextView) view.findViewById(R.id.list_item_text_value);
        list_item_text_created_at = (TextView) view.findViewById(R.id.list_item_text_created_at);
        textViewConcept = (TextView) view.findViewById(R.id.textViewConcept);
        textViewKind = (TextView) view.findViewById(R.id.textViewKind);
        textViewValue = (TextView) view.findViewById(R.id.textViewValue);
        textViewCreatedAt = (TextView) view.findViewById(R.id.textViewDate);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        return view;
    }*/

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

            /*TextView kind = (TextView) getView().findViewById(R.id.list_item_text_kind);

            if(kind.equals("entry"))
                kind.setTextColor(Color.GREEN);
            if(kind.equals("spending"))
                kind.setTextColor(Color.RED);
            else
                kind.setTextColor(Color.BLUE);*/

            Log.d(TAG, "onLoadFinished with cursor: " + data.getCount());
            mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
    }
}

