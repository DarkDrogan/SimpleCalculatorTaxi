package org.drogan.simplecalculatortaxi.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import org.drogan.simplecalculatortaxi.CalculatorActivity;

import java.text.DateFormat;
import java.util.Date;

import static org.drogan.simplecalculatortaxi.SQL.TaxiDBHelper.*;

public class TaxiDBExecute {

    private static TaxiDBHelper dbHelper;
    private static SQLiteDatabase db;
    private static ContentValues contentValues;

    public static boolean initialSQLforWrite(Context context){
        try {
            dbHelper = new TaxiDBHelper(context);
            db = dbHelper.getWritableDatabase();
            contentValues = new ContentValues();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public static boolean initialSQLforRead(Context context){
        try{dbHelper = new TaxiDBHelper(context);
        db = dbHelper.getWritableDatabase();
    } catch (SQLiteException e) {
        return false;
    }
        return true;
    }
        //i think i need variables from activity. How i can do this?
    public static void insertData(int earnedMoney, double expenseGasoline, double costOfGasoline,
                                  double distanse){
        contentValues = new ContentValues();
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance();
        contentValues.put(KEY_DATE, dateFormat.format(date));
        dateFormat = DateFormat.getTimeInstance();
        contentValues.put(KEY_TIME, dateFormat.format(date));
        contentValues.put(KEY_EARNED_MONEY, earnedMoney);
        contentValues.put(KEY_DISTANSE, distanse);
        contentValues.put(KEY_EXPENSE_GASOLINE, expenseGasoline);
        contentValues.put(KEY_COST_GASOLINE_ON_TIME, costOfGasoline);
        db.insert(TABLE_INCOME, null, contentValues);
        db.close();
    }




    public static boolean readInSQL(Context context){
        throw new UnsupportedOperationException("method \"readInSQL\" is not realesed ");
    }
}
