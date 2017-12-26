package com.grupo4.esteban.miscuentas;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsProvider extends ContentProvider {

    private static final String TAG = MyAccountsProvider.class.getSimpleName();
    private DbHelper dbHelper;
    private static final UriMatcher sURIMatcher;

    static {
        sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sURIMatcher.addURI(MyAccountsContract.AUTHORITY, MyAccountsContract.TABLE, MyAccountsContract.STATUS_DIR);
        sURIMatcher.addURI(MyAccountsContract.AUTHORITY, MyAccountsContract.TABLE + "/#", MyAccountsContract.STATUS_ITEM);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        Log.d(TAG, "onCreated");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String where;

        switch (sURIMatcher.match(uri)) {
            case MyAccountsContract.STATUS_DIR:
                where = selection;
                break;
            case MyAccountsContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = MyAccountsContract.Column.ID
                        + "="
                        + id;
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }

        String orderBy = (TextUtils.isEmpty(sortOrder))
                ? MyAccountsContract.DEFAULT_SORT
                : sortOrder;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(MyAccountsContract.TABLE, projection, where, selectionArgs, null, null, orderBy);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d(TAG, "registros recuperados: " + cursor.getCount());
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sURIMatcher.match(uri)) {
            case MyAccountsContract.STATUS_DIR:
                return "vnd.android.cursor.dir/vnd.com.grupo4.esteban.miscuentas.provider.myaccounts";
            case MyAccountsContract.STATUS_ITEM:
                return "vnd.android.cursor.item/vnd.com.grupo4.esteban.miscuentas.provider.myaccounts";
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri ret = null;

// Nos aseguramos de que la URI es correcta
        if (sURIMatcher.match(uri) != MyAccountsContract.STATUS_DIR) {
            throw new IllegalArgumentException("uri incorrecta: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(MyAccountsContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);

// Se inserto correctamente?
        if (rowId != -1) {
            Log.d(TAG, "uri insertada con id: " + values.getAsLong(MyAccountsContract.Column.ID));

// Notificar que los datos para la URI han cambiado
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ret;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String where;

        switch (sURIMatcher.match(uri)) {
            case MyAccountsContract.STATUS_DIR:
                where = selection;
                break;
            case MyAccountsContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = MyAccountsContract.Column.ID
                        + "="
                        + id;
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(MyAccountsContract.TABLE, where, selectionArgs);

        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(TAG, "registros borrados: " + ret);
        return ret;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String where;

        switch (sURIMatcher.match(uri)) {
            case MyAccountsContract.STATUS_DIR:
                where = selection;
                break;
            case MyAccountsContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = MyAccountsContract.Column.ID
                        + "="
                        + id;
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.update(MyAccountsContract.TABLE, values, where, selectionArgs);

        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "registros actualizados: " + ret);
        return ret;
    }
}
