package com.ielts.speaking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.ielts.speaking.publicClasses.PermissionHandler;
import com.ielts.speaking.publicClasses.ProfilePictureManager;
import com.ielts.speaking.publicClasses.phoneLogin;


public class SignUp extends AppCompatActivity {

    EditText nameText, phonetext;
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.themeBack));
        setContentView(R.layout.activity_sign_up);


        Button signUpButton = findViewById(R.id.SignUp);
        Button signInButton = findViewById(R.id.SignIn);
        nameText = findViewById(R.id.signUpName);
        phonetext = findViewById(R.id.SignUpPhone);
        CardView imgPicker = findViewById(R.id.cardView2);
        profileImage = findViewById(R.id.imageView);

        signInButton.setOnClickListener(v -> startActivity(new Intent(SignUp.this, signIn.class)));
        signUpButton.setOnClickListener(v -> {
            if (nameText.getText().toString().isEmpty()) {
                nameText.setError("Please enter your name");
            } else if (phonetext.getText().toString().isEmpty()) {
                phonetext.setError("Please enter your phone number");
            } else {
                phoneLogin.init(SignUp.this, phonetext.getText().toString().trim());
                phoneLogin.startPhoneNumberVerification(phonetext.getText().toString().trim(), SignUp.this);
            }
        });

        imgPicker.setOnClickListener(v -> {
            if (PermissionHandler.checkPermissions(this)) {
                ProfilePictureManager profilePictureManager = new ProfilePictureManager(this);
                profilePictureManager.pickImage();
            } else {
                // Permissions are not granted, request them
                PermissionHandler.requestPermissions(SignUp.this);
            }
            ProfilePictureManager profilePictureManager = new ProfilePictureManager(this);
            profilePictureManager.pickImage();

        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionHandler.PERMISSION_REQUEST_CODE) {
            if (PermissionHandler.handlePermissionsResult(requestCode, grantResults)) {
                // All permissions are granted, proceed with the action
                ProfilePictureManager profilePictureManager = new ProfilePictureManager(this);
                profilePictureManager.pickImage();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ProfilePictureManager profilePictureManager = new ProfilePictureManager(SignUp.this);
        profilePictureManager.onActivityResult(requestCode, resultCode, data);
    }
}

