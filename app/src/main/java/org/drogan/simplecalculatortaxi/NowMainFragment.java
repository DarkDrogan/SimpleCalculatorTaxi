package org.drogan.simplecalculatortaxi;


import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.drogan.simplecalculatortaxi.Logic.CalculationTemporaryExpenseOfGasoline;
import org.drogan.simplecalculatortaxi.SQL.TaxiDBHelper;
import org.drogan.simplecalculatortaxi.model.Shift;

import static android.content.Context.MODE_PRIVATE;
import static org.drogan.simplecalculatortaxi.SQL.TaxiDBExecute.initialSQLForRead;
import static org.drogan.simplecalculatortaxi.SQL.TaxiDBExecute.initialSQLForWrite;
import static org.drogan.simplecalculatortaxi.SQL.TaxiDBExecute.insertData;
import static org.drogan.simplecalculatortaxi.SQL.TaxiDBExecute.readDataButNowItDeleteDataBase;

public class NowMainFragment extends Fragment {
    public static final String EXPENSE_GASOLINE = "EXPENSE_GASOLINE";
    public static final String COST_GASOLINE = "COST_GASOLINE";
    public static final String PERCENT = "percent";

    private float averageExpenseGasoline;
    private float earnedMoney;
    private float distance;
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
    private EditText mTipsET;
    private float tips;
    private String mX;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_calculator, container, false);

        initialVariables();
        onStarted();
        EditTextViewCleanerListener etvc = new EditTextViewCleanerListener();
        Shift shift = Shift.getShift();
        mX = shift.getEarning();
        System.out.println(mX);


        moneyET = (EditText) v.findViewById(R.id.eMoney);
        fuelET = (EditText) v.findViewById(R.id.eFuel);
        costFuelET = (EditText) v.findViewById(R.id.eCostFuel);

        distanseET = (EditText) v.findViewById(R.id.eDistanse);
        mTipsET = (EditText) v.findViewById(R.id.eTips);

        incomeTV = (TextView) v.findViewById(R.id.income);
        profitTV = (TextView)  v.findViewById(R.id.clear_income);
        expenseOnGasolineTV = (TextView) v.findViewById(R.id.expence_gasoline);
        comissionTV = (TextView) v.findViewById(R.id.commission);
        //saveInSQlButton = (Button) v.findViewById(R.id.save);
//        saveInSQlButton.setOnClickListener(this);
        distanseET.setOnFocusChangeListener(etvc);
        moneyET.setOnFocusChangeListener(etvc);
        mTipsET.setOnFocusChangeListener(etvc);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateThis();
                System.out.println(mX);
            }
        };

        moneyET.addTextChangedListener(tw);
        fuelET.addTextChangedListener(tw);
        costFuelET.addTextChangedListener(tw);
        distanseET.addTextChangedListener(tw);
        mTipsET.addTextChangedListener(tw);
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        String text =  sPref.getString(EXPENSE_GASOLINE, "8.5");
        System.out.println(text + " this loaded");
        text = text.replace(",", ".");
        if (text != null) {
            fuelET.setText(String.format("%2.1f", Float.parseFloat(text)));
        }
        String costText = sPref.getString(COST_GASOLINE, "41.40");
        costText = costText.replace(",", ".");
        costFuelET.setText(String.format("%3.2f", Float.parseFloat(costText)));
        percentForApplication = sPref.getInt(PERCENT, 15);
        return v;
    }


    private void initialVariables() {

       /* calculateButton = (Button) findViewById(R.id.calc_button);
        calculateButton.setOnClickListener(this);*/
        /*readSQLButton = (Button) findViewById(R.id.read);
        readSQLButton.setOnClickListener(this);*/



        dbHelper = new TaxiDBHelper(this.getContext());
        cc = new CalculationTemporaryExpenseOfGasoline();

    }


    protected void onStarted() {
        /*super.onStart();
        */
    }

    @Override
    public void onStop() {
        super.onStop();
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(EXPENSE_GASOLINE, fuelET.getText().toString().replace(",", "."));
        editor.putString(COST_GASOLINE, costFuelET.getText().toString().replace(",", "."));
        editor.putInt(PERCENT, percentForApplication);
        editor.commit();
    }


    private void saveInSQL(){
        initialSQLForWrite(this.getContext());
        insertData((int)earnedMoney, averageExpenseGasoline, costOfFuel, distance);
        Toast.makeText(this.getContext(), "saved", Toast.LENGTH_SHORT).show();
    }

    //This is temporary method for set SQLite for application
    protected void readSQL(View view){
        initialSQLForRead(this.getContext());
        readDataButNowItDeleteDataBase();
        Toast.makeText(this.getContext(), "Table was readed", Toast.LENGTH_SHORT).show();
    }


    private void calculateThis(){
        parseEditableText();

        String incomeString = String.format("%4.2f", cc.getIncome() + tips);
        incomeTV.setText(incomeString);
        profitTV.setText(String.format("%4.2f", cc.getProfit() + tips));
        comissionTV.setText(String.format("%4.2f", cc.getWastedMoneyOfPercent()));
        expenseOnGasolineTV.setText(String.format("%4.2f", cc.getExpenseOnGasoline()));
    }

    private void parseEditableText() {
        try {
            averageExpenseGasoline = Float.parseFloat(fuelET.getText().toString().replace(",", "."));
            costOfFuel = Float.parseFloat(costFuelET.getText().toString().replace(",", "."));
        }catch (NumberFormatException e){
            averageExpenseGasoline = 0;
            /*fuelET.setText(String.format("%1.0f", 0f));*/
            costOfFuel = 0;
        }
        try {
            distance = Float.parseFloat(distanseET.getText().toString());
        } catch (NumberFormatException e) {
            distance = 0;
            /*distanseET.setText(String.format("%1.0f", 0f));*/
        }
        try {
            tips = Float.parseFloat(mTipsET.getText().toString().replace(",", "."));
        } catch (NumberFormatException e) {
            tips = 0;
            /*mTipsET.setText(String.format("%1.0f", 0f));*/
        }

        try {
            earnedMoney = Float.parseFloat(moneyET.getText().toString());
        } catch (NumberFormatException e) {
            earnedMoney = 0;
            /*moneyET.setText(String.format("%1.0f", 0f));*/
        }
        cc.setVariablesAndCalculateData(averageExpenseGasoline, earnedMoney, distance, costOfFuel, percentForApplication);
    }
}
