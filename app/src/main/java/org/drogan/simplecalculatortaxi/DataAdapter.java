package org.drogan.simplecalculatortaxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DataAdapter extends ArrayAdapter<String> {


    //list of content, can be another(DB)
    private static String[] mContacts = { "Рыжик", "Барсик", "Мурзик",
            "Мурка", "Васька", "Полосатик", "Матроскин", "Лизка", "Томосина",
            "Бегемот", "Чеширский", "Дивуар", "Тигра", "Лаура" };

    private static String[] mData;


    //have it for sent?
    Context mContext;

    // Конструктор
    public DataAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId, mContacts);
        // TODO Auto-generated constructor stub
        this.mContext = context;
    }
    public DataAdapter(Context context, int textViewResourceId, String[] data) {
        super(context, textViewResourceId, data);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        mContacts = data;
    }

    public DataAdapter(Context context, int textViewResourceId, String[] data, String[] dopData) {
        super(context, textViewResourceId, data);
        // TODO Auto-generated constructor stub
        this.mContext = context;
        mContacts = data;
        mData = dopData;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        TextView label = (TextView) convertView;

        if (convertView == null) {
            convertView = new TextView(mContext);
            label = (TextView) convertView;
        }
        label.setText(mContacts[position]);
        return (convertView);
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View grid;

        if (convertView == null) {
            grid = new View(mContext);
            //LayoutInflater inflater = getLayoutInflater();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            grid = inflater.inflate(R.layout.for_grid_table, parent, false);
        } else {
            grid = (View) convertView;
        }

        TextView textView = (TextView) grid.findViewById(R.id.textpart);
        TextView textView1 = (TextView) grid.findViewById(R.id.text1);
        textView.setText(mContacts[position]);
        textView1.setText(mData[position]);

        return grid;
    }

    // возвращает содержимое выделенного элемента списка
    public String getItem(int position) {
        return mContacts[position];
    }

}