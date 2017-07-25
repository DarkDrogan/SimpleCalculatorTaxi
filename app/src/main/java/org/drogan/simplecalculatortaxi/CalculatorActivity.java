package org.drogan.simplecalculatortaxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    private double aFuel;
    private double aMoney;
    private double aDistanse;
    private double aCostFuel;
    private EditText moneyET;
    private EditText fuelET;
    private EditText costFuelET;
    private EditText distanseET;
    private TextView incomeTV;
    private TextView clearIncomeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void cancelInfo(View view){
        //NOP
        onDestroy();
    }

    public void saveInSQL(View view){
        //NOP
    }


    public void calculateThis(View view){
        moneyET = (EditText) findViewById(R.id.eMoney);
        fuelET = (EditText) findViewById(R.id.eFuel);
        costFuelET = (EditText) findViewById(R.id.eCostFuel);
        distanseET = (EditText) findViewById(R.id.eDistanse);
        incomeTV = (TextView) findViewById(R.id.income);
        clearIncomeTV = (TextView) findViewById(R.id.clear_income);

        final TextView expenceOnGasoline = (TextView) findViewById(R.id.expence_gasoline);
        final TextView comission = (TextView) findViewById(R.id.commission);

        try {
            aFuel = Double.parseDouble(fuelET.getText().toString());
            aCostFuel = Double.parseDouble(costFuelET.getText().toString());
        }catch (NumberFormatException e){
            aFuel = 0;
            aCostFuel = 0;
        }
        try {
            aDistanse = Double.parseDouble(distanseET.getText().toString());
        } catch (NumberFormatException e) {
            aDistanse = 0;
        }

        try {
            aMoney = Double.parseDouble(moneyET.getText().toString());
        } catch (NumberFormatException e) {
            aMoney = 0;
        }
        double moneyOnFuel = (aFuel / 100.0 * aDistanse * aCostFuel);
        double infoStatistic = aMoney - moneyOnFuel;
        double percent = aMoney / 100.0 * 15;

        String incomeString = String.format("%4.2f", infoStatistic);
        incomeTV.setText(incomeString);
        clearIncomeTV.setText(String.format("%4.2f", infoStatistic != 0 ? infoStatistic - percent : infoStatistic));
        comission.setText(String.format("%4.2f", percent));
        expenceOnGasoline.setText(String.format("%4.2f", moneyOnFuel));
    }
}
