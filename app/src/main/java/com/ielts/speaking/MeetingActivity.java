package com.ielts.speaking;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);



        Button btnJoin = findViewById(R.id.btnJoin);

        btnJoin.setOnClickListener(v -> {

        });

    }


}