package org.drogan.simplecalculatortaxi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.drogan.simplecalculatortaxi.Logic.CalculationTemporaryExpenseOfGasoline;
import org.drogan.simplecalculatortaxi.SQL.TaxiDBHelper;

import static org.drogan.simplecalculatortaxi.SQL.TaxiDBExecute.*;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXPENSE_GASOLINE = "EXPENSE_GASOLINE";
    public static final String COST_GASOLINE = "COST_GASOLINE";
    public static final String PERCENT = "percent";

    private float averageExpenseGasoline;
    private float earnedMoney;
    private float distanse;
    private float costOfFuel;

    private int percentForApplication;

    private EditText moneyET;
    private EditText fuelET;
    private EditText costFuelET;
    private EditText distanseET;

    private TextView incomeTV;
    private TextView profitTV;
    private TextView expenseOnGasolineTV;
    private TextView comissionTV;

    private Button saveInSQlButton, calculateButton;
    private Button readSQLButton;

    SharedPreferences sPref;

    private SQLiteDatabase db;
    private TaxiDBHelper dbHelper;
    private CalculationTemporaryExpenseOfGasoline cc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);


        initialVariables();
        onStarted();
    }

    private void initialVariables() {
        saveInSQlButton = (Button) findViewById(R.id.save);
        saveInSQlButton.setOnClickListener(this);
        calculateButton = (Button) findViewById(R.id.calc_button);
        calculateButton.setOnClickListener(this);
        /*readSQLButton = (Button) findViewById(R.id.read);
        readSQLButton.setOnClickListener(this);*/

        moneyET = (EditText) findViewById(R.id.eMoney);
        fuelET = (EditText) findViewById(R.id.eFuel);
        costFuelET = (EditText) findViewById(R.id.eCostFuel);
        distanseET = (EditText) findViewById(R.id.eDistanse);

        incomeTV = (TextView) findViewById(R.id.income);
        profitTV = (TextView)  findViewById(R.id.clear_income);
        expenseOnGasolineTV = (TextView) findViewById(R.id.expence_gasoline);
        comissionTV = (TextView) findViewById(R.id.commission);

        dbHelper = new TaxiDBHelper(CalculatorActivity.this);
        cc = new CalculationTemporaryExpenseOfGasoline();
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
        percentForApplication = sPref.getInt(PERCENT, 15);
    }

    @Override
    protected void onStop() {
        super.onPause();
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(EXPENSE_GASOLINE, fuelET.getText().toString().replace(",", "."));
        editor.putString(COST_GASOLINE, costFuelET.getText().toString().replace(",", "."));
        editor.putInt(PERCENT, percentForApplication);
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
            /*case R.id.setPercent: setPercent();
                break;*/
        }
    }

    protected void setPercent(View view) {
        /*Dialog dialogSetPercent = new Dialog(CalculatorActivity.this);
        dialogSetPercent.setTitle("set percent");
        dialogSetPercent.setContentView(R.layout.dialog_view);
        dialogSetPercent.show();*/
        Intent intent = new Intent(this, ReportIncome.class);
        startActivity(intent);
    }


    private void saveInSQL(){
        initialSQLForWrite(CalculatorActivity.this);
        insertData((int)earnedMoney, averageExpenseGasoline, costOfFuel, distanse);
        Toast.makeText(CalculatorActivity.this, "saved", Toast.LENGTH_SHORT).show();
    }

    //This is temporary method for set SQLite for application
    protected void readSQL(View view){
        initialSQLForRead(CalculatorActivity.this);
        readDataButNowItDeleteDataBase();
        Toast.makeText(CalculatorActivity.this, "Table was readed", Toast.LENGTH_SHORT).show();
    }


    private void calculateThis(){



        parseEditableText();

        String incomeString = String.format("%4.2f", cc.getIncome());
        incomeTV.setText(incomeString);
        profitTV.setText(String.format("%4.2f", cc.getProfit()));
        comissionTV.setText(String.format("%4.2f", cc.getWastedMoneyOfPercent()));
        expenseOnGasolineTV.setText(String.format("%4.2f", cc.getExpenseOnGasoline()));
    }

    private void parseEditableText() {
        try {
            averageExpenseGasoline = Float.parseFloat(fuelET.getText().toString().replace(",", "."));
            costOfFuel = Float.parseFloat(costFuelET.getText().toString().replace(",", "."));
        }catch (NumberFormatException e){
            averageExpenseGasoline = 0;
            fuelET.setText(String.format("%4.1f", 0f));
            costOfFuel = 0;
        }
        try {
            distanse = Float.parseFloat(distanseET.getText().toString());
        } catch (NumberFormatException e) {
            distanse = 0;
            distanseET.setText(String.format("%4.1f", 0f));
        }

        try {
            earnedMoney = Float.parseFloat(moneyET.getText().toString());
        } catch (NumberFormatException e) {
            earnedMoney = 0;
            moneyET.setText(String.format("%4.2f", 0f));
        }
        cc.setVariablesAndCalculateData(averageExpenseGasoline, earnedMoney, distanse, costOfFuel, percentForApplication);
    }
}
