package org.drogan.simplecalculatortaxi;


import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.drogan.simplecalculatortaxi.Logic.CalculationTemporaryExpenseOfGasoline;
import org.drogan.simplecalculatortaxi.Logic.EditTextViewCleanerListener;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_calculator, container, false);


        initialVariables();
        EditTextViewCleanerListener etvc = new EditTextViewCleanerListener();



        moneyET = (EditText) v.findViewById(R.id.eMoney);
        fuelET = (EditText) v.findViewById(R.id.eFuel);
        costFuelET = (EditText) v.findViewById(R.id.eCostFuel);
        distanseET = (EditText) v.findViewById(R.id.eDistanse);
        mTipsET = (EditText) v.findViewById(R.id.eTips);
        incomeTV = (TextView) v.findViewById(R.id.income);
        profitTV = (TextView)  v.findViewById(R.id.clear_income);
        expenseOnGasolineTV = (TextView) v.findViewById(R.id.expence_gasoline);
        comissionTV = (TextView) v.findViewById(R.id.commission);

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
        dbHelper = new TaxiDBHelper(this.getContext());
        cc = new CalculationTemporaryExpenseOfGasoline();
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
        parseEditableTextLASTVERSION();

        String incomeString = String.format("%4.2f", cc.getIncome() + tips);
        incomeTV.setText(incomeString);
        profitTV.setText(String.format("%4.2f", cc.getProfit() + tips));
        comissionTV.setText(String.format("%4.2f", cc.getWastedMoneyOfPercent()));
        expenseOnGasolineTV.setText(String.format("%4.2f", cc.getExpenseOnGasoline()));
    }

    private void parseEditableTextLASTVERSION() {
        averageExpenseGasoline = parseEditableText(fuelET);
        costOfFuel = parseEditableText(costFuelET);
        distance = parseEditableText(distanseET);
        tips = parseEditableText(mTipsET);
        earnedMoney = parseEditableText(moneyET);
        Shift shift = new Shift(earnedMoney, distance, tips, averageExpenseGasoline, costOfFuel);
//        cc.setVariablesAndCalculateData(averageExpenseGasoline, earnedMoney, distance, costOfFuel, percentForApplication);
        cc.setVariablesAndCalculateData(shift);
    }

    private float parseEditableText(EditText et){
        float x = 0;
        try {
            x = Float.parseFloat(et.getText().toString().replace(",", "."));
        } catch (NumberFormatException e) {
            Log.e("ETAG", "parsing EditText have a NumberFormatException: \n" + e.toString());
        }
        return x;
    }
}
