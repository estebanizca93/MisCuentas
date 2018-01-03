package com.grupo4.esteban.miscuentas;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsContract {
    //Base de datos
    public static final String DB_NAME = "myaccounts.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "myaccounts";
    public static final String DEFAULT_SORT = Column.CREATED_AT + " DESC";

    // Constantes del content provider
    public static final String AUTHORITY = "com.grupo4.esteban.miscuentas.MyAccountsProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int STATUS_ITEM = 1;
    public static final int STATUS_DIR = 2;

    //Se definen las columnas(atributos) de la entidad de la base de datos.
    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String CONCEPT = "concept";
        public static final String KIND = "kind";
        public static final String VALUE = "value";
        public static final String CREATED_AT = "created_at";
    }
}
