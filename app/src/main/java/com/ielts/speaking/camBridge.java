package com.ielts.speaking;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ielts.speaking.publicClasses.AirtableApiClient;
import com.ielts.speaking.publicClasses.SqliteDataHelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class camBridge extends Fragment {
    private Context context;
    public String TAG = "AirtableApiClient";
    private Activity activity;
    ArrayList<String> booksAnswer = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cam_bridge, container, false);




        TextView ansWer1_textVIew = view.findViewById(R.id.Answer1);
        TextView ansWer2_textVIew = view.findViewById(R.id.Answer2);
        TextView ansWer3_textVIew = view.findViewById(R.id.Answer3);
        TextView ansWer4_textVIew = view.findViewById(R.id.Answer4);
        Spinner spinner = view.findViewById(R.id.spinner);

        try {
            SqliteDataHelp sqliteDataHelp = new SqliteDataHelp(this.context);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, sqliteDataHelp.getDataList());
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ansWer1_textVIew.setText(sqliteDataHelp.ANSWER_LIST.get(position).getAsString(sqliteDataHelp.COLUMN_1));
                    ansWer2_textVIew.setText(sqliteDataHelp.ANSWER_LIST.get(position).getAsString(sqliteDataHelp.COLUMN_2));
                    ansWer3_textVIew.setText(sqliteDataHelp.ANSWER_LIST.get(position).getAsString(sqliteDataHelp.COLUMN_3));
                    ansWer4_textVIew.setText(sqliteDataHelp.ANSWER_LIST.get(position).getAsString(sqliteDataHelp.COLUMN_4));



                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle the case when no item is selected
                }
            });
        }catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return view;
    }


}
