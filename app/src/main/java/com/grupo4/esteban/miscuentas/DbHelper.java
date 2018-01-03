package com.grupo4.esteban.miscuentas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Esteban on 26/12/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    // Constructor
    public DbHelper(Context context) {
        super(context, MyAccountsContract.DB_NAME, null, MyAccountsContract.DB_VERSION);
    }

    //Llamado para crear la tabla
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s integer primary key autoincrement, %s text, %s text, %s double, %s string)",
                MyAccountsContract.TABLE,
                MyAccountsContract.Column.ID,
                MyAccountsContract.Column.CONCEPT,
                MyAccountsContract.Column.KIND,
                MyAccountsContract.Column.VALUE,
                MyAccountsContract.Column.CREATED_AT);

        Log.d(TAG, "onCreate con SQL: " + sql);

        db.execSQL(sql);
    }

    // Llamado siempre que tengamos una nueva version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + MyAccountsContract.TABLE); // borra la vieja base de datos
        onCreate(db); // crea una base de datos nueva
        Log.d(TAG, "onUpgrade");
    }
}
