package com.grupo4.esteban.miscuentas;

import android.provider.BaseColumns;

/**
 * Created by Esteban on 26/12/2017.
 */

public class MyAccountsContract {
    public static final String DB_NAME = "myaccounts.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "myaccounts";

    public static final String DEFAULT_SORT = Column.CREATED_AT + " DESC";

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String CONCEPT = "concept";
        public static final String KIND = "kind";
        public static final String VALUE = "value";
        public static final String CREATED_AT = "created_at";
    }
}
