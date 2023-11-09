package com.ielts.speaking;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class signIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button signUpButtons = findViewById(R.id.SignUps);
        Button signInButtons = findViewById(R.id.SignIns);

        signUpButtons.setOnClickListener(v -> this.finish());

    }



}