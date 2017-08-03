package org.drogan.simplecalculatortaxi.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Date;


public class TaxiDBHelper extends SQLiteOpenHelper {
    private static SQLiteDatabase db;

    public static final String DB_TAXI = "taxo_db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_INCOME = "table_income";

    public static final String KEY_ID = "_id";
    public static final String KEY_DATE = "workdate";
    public static final String KEY_TIME = "worktime";
    public static final String KEY_EARNED_MONEY = "earnedmoney";
    public static final String KEY_DISTANSE = "distanse";
    public static final String KEY_EXPENSE_GASOLINE = "expensegasoline";
    public static final String KEY_COST_GASOLINE_ON_TIME = "costgasoline";


    public TaxiDBHelper(Context context) {
        super(context, DB_TAXI, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_INCOME + "(" + KEY_ID + " integer primary key autoincrement, "
                + KEY_DATE + " text, " + KEY_TIME + " text, " + KEY_EARNED_MONEY + " integer, "
                + KEY_DISTANSE + " decimal, " + KEY_EXPENSE_GASOLINE + " decimal, " +
                KEY_COST_GASOLINE_ON_TIME + " decimal)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exist " + TABLE_INCOME);
        onCreate(db);
    }


}
