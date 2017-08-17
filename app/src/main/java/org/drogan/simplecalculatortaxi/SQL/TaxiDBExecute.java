package org.drogan.simplecalculatortaxi.SQL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.drogan.simplecalculatortaxi.SQL.TaxiDBHelper.*;

public class TaxiDBExecute {

    private static TaxiDBHelper dbHelper;
    private static SQLiteDatabase db;
    private static ContentValues contentValues;
    private static Context context1;

    public static boolean initialSQLForWrite(Context context){
        try {
            context1 = context;
            dbHelper = new TaxiDBHelper(context);
            db = dbHelper.getWritableDatabase();
            contentValues = new ContentValues();
        } catch (SQLiteException e) {
            return false;
        }
        return true;
    }

    public static boolean initialSQLForRead(Context context){
        context1 = context;
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
        Locale locale = new Locale("ru", "RU");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
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

    public static void readDataButNowItDeleteDataBase(){
        db.delete(TaxiDBHelper.TABLE_INCOME, null, null);
//        Toast.makeText(CalculatorActivity.this, "Database was deleted", Toast.LENGTH_SHORT).show();
        /*Cursor cursor = db.query(TaxiDBHelper.TABLE_INCOME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_ID);
            int nameIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_DATE);
            int emailIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_TIME);
            int earnedMoneyIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_EARNED_MONEY);
            do {
                Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                        ", " + cursor.getString(nameIndex) +
                        ", " + cursor.getString(emailIndex) +
                        ", money: " + cursor.getString(earnedMoneyIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }*/

        /*cursor.close();*/
        db.close();
    }

    public static String[] readDataArrayString(){
        /*db.delete(TaxiDBHelper.TABLE_INCOME, null, null);
        Toast.makeText(CalculatorActivity.this, "Database was deleted", Toast.LENGTH_SHORT).show();*/
        List<String> list = new ArrayList<>();
        int index = 0;
        Cursor cursor = db.query(TaxiDBHelper.TABLE_INCOME, null, null, null, null, null, null);

        if(cursor.moveToLast()){
            int idIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_ID);
            index = cursor.getInt(idIndex);
        }

        String [] strings = new String[index*4+4];
        index = 0;


        //if this will be unlocked then +string for array length
        strings[index++] = "date";
        strings[index++] = "time";
        strings[index++] = "distance";
        strings[index++] = "profit";

        if (cursor.moveToFirst()) {

            int dateIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_TIME);
            int earnedMoneyIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_EARNED_MONEY);
            int costOfGasolineIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_COST_GASOLINE_ON_TIME);
            int AverageExpenceOfGasolineIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_EXPENSE_GASOLINE);
            int distanseIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_DISTANSE);
            do {
                strings[index++] = (cursor.getString(dateIndex));
                strings[index++] = (cursor.getString(timeIndex));
                strings[index++] = (cursor.getString(distanseIndex));
                double expenseOfGasoline = cursor.getDouble(AverageExpenceOfGasolineIndex) / 100 *
                        cursor.getDouble(distanseIndex) * cursor.getDouble(costOfGasolineIndex);
                int earnedMoney = cursor.getInt(earnedMoneyIndex);
                double profit = earnedMoney - (earnedMoney / 100 * 15) - expenseOfGasoline;
                strings[index++] = String.format("%4.2f", profit);
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }

        cursor.close();
        db.close();
        return strings;
    }
    public static String[] readDataArrayStringProfit(){
        /*db.delete(TaxiDBHelper.TABLE_INCOME, null, null);
        Toast.makeText(CalculatorActivity.this, "Database was deleted", Toast.LENGTH_SHORT).show();*/
        if(!db.isOpen()){
            initialSQLForRead(context1);
        }
        List<String> list = new ArrayList<>();
        int index = 0;
        Cursor cursor = db.query(TaxiDBHelper.TABLE_INCOME, null, null, null, null, null, null);

        if(cursor.moveToLast()){
            int idIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_ID);
            index = cursor.getInt(idIndex);
        }

        String [] strings = new String[index];
        index = 0;


        //if this will be unlocked then +string for array length
        /*strings[index++] = "date";
        strings[index++] = "time";
        strings[index++] = "distance";
        strings[index++] = "profit";*/

        if (cursor.moveToFirst()) {

            int dateIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_TIME);
            int earnedMoneyIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_EARNED_MONEY);
            int costOfGasolineIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_COST_GASOLINE_ON_TIME);
            int AverageExpenceOfGasolineIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_EXPENSE_GASOLINE);
            int distanseIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_DISTANSE);
            do {
                /*strings[index++] = (cursor.getString(dateIndex));
                strings[index++] = (cursor.getString(timeIndex));
                strings[index++] = (cursor.getString(distanseIndex));*/
                double expenseOfGasoline = cursor.getDouble(AverageExpenceOfGasolineIndex) / 100 *
                        cursor.getDouble(distanseIndex) * cursor.getDouble(costOfGasolineIndex);
                int earnedMoney = cursor.getInt(earnedMoneyIndex);
                double profit = earnedMoney - (earnedMoney / 100 * 15) - expenseOfGasoline;
                strings[index++] = String.format("%4.2f", profit);
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }

        cursor.close();
        db.close();
        return strings;
    }
    public static String[] readDataArrayStringDate(){
        /*db.delete(TaxiDBHelper.TABLE_INCOME, null, null);
        Toast.makeText(CalculatorActivity.this, "Database was deleted", Toast.LENGTH_SHORT).show();*/
        List<String> list = new ArrayList<>();
        int index = 0;
        Cursor cursor = db.query(TaxiDBHelper.TABLE_INCOME, null, null, null, null, null, null);

        if(cursor.moveToLast()){
            int idIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_ID);
            index = cursor.getInt(idIndex);
        }

        String [] strings = new String[index];
        index = 0;


        //if this will be unlocked then +string for array length
        /*strings[index++] = "date";
        strings[index++] = "time";
        strings[index++] = "distance";
        strings[index++] = "profit";*/

        if (cursor.moveToFirst()) {

            int dateIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_DATE);
            int timeIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_TIME);
            int earnedMoneyIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_EARNED_MONEY);
            int costOfGasolineIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_COST_GASOLINE_ON_TIME);
            int AverageExpenceOfGasolineIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_EXPENSE_GASOLINE);
            int distanseIndex = cursor.getColumnIndex(TaxiDBHelper.KEY_DISTANSE);
            do {
                strings[index++] = (cursor.getString(dateIndex));
                /*strings[index++] = (cursor.getString(timeIndex));
                strings[index++] = (cursor.getString(distanseIndex));*/
                /*double expenseOfGasoline = cursor.getDouble(AverageExpenceOfGasolineIndex) / 100 *
                        cursor.getDouble(distanseIndex) * cursor.getDouble(costOfGasolineIndex);
                int earnedMoney = cursor.getInt(earnedMoneyIndex);
                double profit = earnedMoney - (earnedMoney / 100 * 15) - expenseOfGasoline;
                strings[index++] = String.format("%4.2f", profit);*/
            } while (cursor.moveToNext());
        } else {
            Log.d("mLog", "0 rows");
        }

        cursor.close();
        db.close();
        return strings;
    }




    public static boolean readInSQL(Context context){
        throw new UnsupportedOperationException("method \"readInSQL\" is not realesed ");
    }
}
