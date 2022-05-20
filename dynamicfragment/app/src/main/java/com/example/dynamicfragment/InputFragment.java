package com.example.dynamicfragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class InputFragment extends Fragment implements View.OnClickListener{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button1 = (Button) getView().findViewById(R.id.button1);
        button1.setOnClickListener(this);

        Button button2 = (Button) getView().findViewById(R.id.button2);
        button2.setOnClickListener(this);

        Button button3 = (Button) getView().findViewById(R.id.button3);
        button3.setOnClickListener(this);

        Button button4 = (Button) getView().findViewById(R.id.button4);
        button4.setOnClickListener(this);

        Button button5 = (Button) getView().findViewById(R.id.button5);
        button5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        EditText name = (EditText) getView().findViewById(R.id.name);
        String nameValue = name.getText().toString();

        String buttonNum = "";
        switch (v.getId()){
            case R.id.button1:
                buttonNum = "1";
                break;
            case R.id.button2:
                buttonNum = "2";
                break;
            case R.id.button3:
                buttonNum = "3";
                break;
            case R.id.button4:
                buttonNum = "4";
                break;
            case R.id.button5:
                buttonNum = "5";
                break;

        }
        String message = "Hello, " + nameValue + "! Your taped button №" + buttonNum;
        MessageFragment fragment = (MessageFragment)getFragmentManager().findFragmentById(R.id.fragment_detail);
        if (fragment != null && fragment.isInLayout()) {
            fragment.setMessage(message);
        } else {
            Intent intent = new Intent(getActivity().getApplicationContext(), MessageActivity.class);
            intent.putExtra("value", message);
            startActivity(intent);
        }
    }
}