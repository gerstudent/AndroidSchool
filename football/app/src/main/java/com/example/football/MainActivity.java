package com.example.football;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    DBMatches mDBConnector;
    Context mContext;
    ListView mListView;
    SimpleCursorAdapter scAdapter;
    Cursor cursor;
    myListAdapter myAdapter;

    int ADD_ACTIVITY = 0;
    int UPDATE_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        mDBConnector=new DBMatches(this);
        mListView=(ListView)findViewById(R.id.list);
        myAdapter=new myListAdapter(mContext,mDBConnector.selectAll());
        mListView.setAdapter(myAdapter);
        registerForContextMenu(mListView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent i = new Intent(mContext, AddActivity.class);
                startActivityForResult (i, ADD_ACTIVITY);
                updateList();
                return true;
            case R.id.deleteAll:
                mDBConnector.deleteAll();
                updateList();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit:
                Intent i = new Intent(mContext, AddActivity.class);
                Matches md = mDBConnector.select(info.id);
                i.putExtra("Matches", md);
                startActivityForResult(i, UPDATE_ACTIVITY);
                updateList();
                return true;
            case R.id.delete:
                mDBConnector.delete (info.id);
                updateList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    private void updateList () {
        myAdapter.setArrayMyData(mDBConnector.selectAll());
        myAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            Matches md = (Matches) data.getExtras().getSerializable("Matches");
            if (requestCode == UPDATE_ACTIVITY)
                mDBConnector.update(md);
            else
                mDBConnector.insert(md.getTeamhouse(), md.getTeamguest(), md.getGoalshouse(), md.getGoalsguest());
            updateList();
        }
    }

    class myListAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;
        private ArrayList<Matches> arrayMyMatches;

        public myListAdapter (Context ctx, ArrayList<Matches> arr) {
            mLayoutInflater = LayoutInflater.from(ctx);
            setArrayMyData(arr);
        }

        public ArrayList<Matches> getArrayMyData() {
            return arrayMyMatches;
        }

        public void setArrayMyData(ArrayList<Matches> arrayMyData) {
            this.arrayMyMatches = arrayMyData;
        }

        public int getCount () {
            return arrayMyMatches.size();
        }

        public Object getItem (int position) {

            return position;
        }

        public long getItemId (int position) {
            Matches md = arrayMyMatches.get(position);
            if (md != null) {
                return md.getId();
            }
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.item, null);

            TextView vTeamHome= (TextView)convertView.findViewById(R.id.TeamHome);
            TextView vTeamGuest = (TextView)convertView.findViewById(R.id.TeamGuest);
            TextView vTotal=(TextView)convertView.findViewById(R.id.TeamTotal);


            Matches md = arrayMyMatches.get(position);
            vTeamHome.setText(md.getTeamhouse());
            vTeamGuest.setText(md.getTeamguest());
            vTotal.setText(md.getGoalshouse()+":"+md.getGoalsguest());

            return convertView;
        }
    } // end myAdapter

}