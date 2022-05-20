package com.example.dynamicfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MessageFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);
        return view;
    }

    public void setMessage(String item){
        TextView message = (TextView) getView().findViewById(R.id.message);
        message.setText(item);
    }
}