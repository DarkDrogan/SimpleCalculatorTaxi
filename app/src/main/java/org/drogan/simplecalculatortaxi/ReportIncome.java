package org.drogan.simplecalculatortaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import org.drogan.simplecalculatortaxi.SQL.TaxiDBExecute;

public class ReportIncome extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {

    private DataAdapter mAdapter;
    private Button buttonBackReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_income);

        final GridView g = (GridView) findViewById(R.id.income_gv);

        //// TODO: 04.08.17 here try to create String[] array for DataAdapter, for appereance table with incomes
        TaxiDBExecute.initialSQLForRead(ReportIncome.this);
        String [] args = TaxiDBExecute.readDataArrayStringDate();

        //// TODO: 04.08.17 and here use new construcor with 3 params
        mAdapter = new DataAdapter(getApplicationContext(),
                /*android.R.layout.simple_list_item_1*/R.layout.for_grid_table, args, TaxiDBExecute.readDataArrayStringProfit());
        g.setAdapter(mAdapter);
        g.setOnItemSelectedListener(this);
        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                /*mSelectText.setText("Выбранный элемент: "
                        + mAdapter.getItem(position));*/
                Intent intent = new Intent(ReportIncome.this, DetailerReportActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                //делим позицию на 3 и загружаем новую вкладку отчета
                // TODO: 05.08.17 create detail_report_activity 
                // TODO: 05.08.17 вызов активности и получение метода
            }
        });

        initialButtons();
    }


    public void onClickBackButton(View v) {
        switch (v.getId()){
            case R.id.buttonBackReport: finish();
            break;
        }
    }

    private void initialButtons() {
        Button button = (Button) findViewById(R.id.buttonBackReport);
    }

    //click on item - open report current income
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /*mSelectText.setText("Выбранный элемент: " + mAdapter.getItem(position));*/
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        /*mSelectText.setText("Выбранный элемент: ничего");*/
    }
}
