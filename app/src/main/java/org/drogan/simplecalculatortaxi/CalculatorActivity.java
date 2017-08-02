package org.drogan.simplecalculatortaxi;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.drogan.simplecalculatortaxi.SQL.TaxiDBHelper;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXPENSE_GASOLINE = "EXPENSE_GASOLINE";
    public static final String COST_GASOLINE = "COST_GASOLINE";

    private double aFuel;
    private double earnedMoney;
    private double distanse;
    private double aCostFuel;
    private EditText moneyET;
    private EditText fuelET;
    private EditText costFuelET;
    private EditText distanseET;
    private TextView incomeTV;
    private TextView clearIncomeTV;
    Button saveInSQlButton, calculateButton;
    SharedPreferences sPref;
    private double moneyOnFuel;
    private double infoStatistic;
    private double percent;
    private SQLiteDatabase db;
    private TaxiDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        saveInSQlButton = (Button) findViewById(R.id.save);
        saveInSQlButton.setOnClickListener(this);
        calculateButton = (Button) findViewById(R.id.calc_button);
        calculateButton.setOnClickListener(this);

        moneyET = (EditText) findViewById(R.id.eMoney);
        fuelET = (EditText) findViewById(R.id.eFuel);
        costFuelET = (EditText) findViewById(R.id.eCostFuel);
        distanseET = (EditText) findViewById(R.id.eDistanse);
        incomeTV = (TextView) findViewById(R.id.income);
        clearIncomeTV = (TextView) findViewById(R.id.clear_income);
        onStarted();
    }


    protected void onStarted() {
        super.onStart();
        sPref = getPreferences(MODE_PRIVATE);
        String text =  sPref.getString(EXPENSE_GASOLINE, "8.5");
        System.out.println(text + " this loaded");
        text = text.replace(",", ".");
        fuelET.setText(String.format("%4.1f", Float.parseFloat(text)));
        String costText = sPref.getString(COST_GASOLINE, "41.40");
        costText = costText.replace(",", ".");
        costFuelET.setText(String.format("%4.2f", Float.parseFloat(costText)));
    }

    @Override
    protected void onStop() {
        super.onPause();
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(EXPENSE_GASOLINE, fuelET.getText().toString().replace(",", "."));
        editor.putString(COST_GASOLINE, costFuelET.getText().toString().replace(",", "."));
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.calc_button: calculateThis();
                break;
            case R.id.save: saveInSQL();
                break;
            /*case R.id.read:
                readSQL();
                break;*/
        }
    }


    private void saveInSQL(){
        //NOP
        dbHelper = new TaxiDBHelper(CalculatorActivity.this);
        db = dbHelper.getWritableDatabase();
        TaxiDBHelper.insertData(db, (int)earnedMoney, aFuel, aCostFuel, distanse);
        Toast.makeText(CalculatorActivity.this, "saved", Toast.LENGTH_SHORT).show();
    }

    protected void OnClickReadSQL(View view){
        /*db.delete(TaxiDBHelper.TABLE_INCOME, null, null);
        Toast.makeText(CalculatorActivity.this, "Database was deleted", Toast.LENGTH_SHORT).show();*/
        Cursor cursor = db.query(TaxiDBHelper.TABLE_INCOME, null, null, null, null, null, null);

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
        } else
            Log.d("mLog","0 rows");

        cursor.close();
    }


    private void calculateThis(){


        final TextView expenceOnGasoline = (TextView) findViewById(R.id.expence_gasoline);
        final TextView comission = (TextView) findViewById(R.id.commission);

        try {
            aFuel = Double.parseDouble(fuelET.getText().toString().replace(",", "."));
            aCostFuel = Double.parseDouble(costFuelET.getText().toString().replace(",", "."));
        }catch (NumberFormatException e){
            aFuel = 0;
            fuelET.setText(String.format("%4.1f", 0f));
            aCostFuel = 0;
        }
        try {
            distanse = Double.parseDouble(distanseET.getText().toString());
        } catch (NumberFormatException e) {
            distanse = 0;
            distanseET.setText(String.format("%4.1f", 0f));
        }

        try {
            earnedMoney = Double.parseDouble(moneyET.getText().toString());
        } catch (NumberFormatException e) {
            earnedMoney = 0;
            moneyET.setText(String.format("%4.2f", 0f));
        }
        moneyOnFuel = (aFuel / 100.0 * distanse * aCostFuel);
        infoStatistic = earnedMoney - moneyOnFuel;
        percent = earnedMoney / 100.0 * 15;

        String incomeString = String.format("%4.2f", infoStatistic);
        incomeTV.setText(incomeString);
        clearIncomeTV.setText(String.format("%4.2f", infoStatistic != 0 ? infoStatistic - percent : infoStatistic));
        comission.setText(String.format("%4.2f", percent));
        expenceOnGasoline.setText(String.format("%4.2f", moneyOnFuel));
    }
}
