package org.drogan.simplecalculatortaxi.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TaxiDBExecute {

    private static TaxiDBHelper dbHelper;
    private static SQLiteDatabase db;
    private static ContentValues contentValues;

    public static boolean saveInSQL(Context context){
        dbHelper = new TaxiDBHelper(context);
        db = dbHelper.getWritableDatabase();
        contentValues = new ContentValues();

        //i think i need variables from activity. How i can do this?


        return false;
    }

    public static boolean readInSQL(Context context){
        throw new UnsupportedOperationException("method \"readInSQL\" is not realesed ");
    }
}
