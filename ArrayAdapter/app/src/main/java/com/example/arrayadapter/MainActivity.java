package com.example.arrayadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity{

    String[] myArr = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> monthAdapter =  new ArrayAdapter<String>(this,                                                                                                                                           android.R.layout.simple_list_item_1, myArr);
        setListAdapter(monthAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String month = (String) getListAdapter().getItem(position);
        Toast.makeText(this, month, Toast.LENGTH_SHORT).show();
    }

}