package com.ielts.speaking;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.hbb20.CountryCodePicker;
import com.ielts.speaking.publicClasses.PermissionHandler;
import com.ielts.speaking.publicClasses.phoneLogin;
import com.theartofdev.edmodo.cropper.CropImageView;


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
//        cropImageView = findViewById(R.id.cropImageView);



        signInButton.setOnClickListener(v -> startActivity(new Intent(SignUp.this, signIn.class)));

        signUpButton.setOnClickListener(v -> {

            String username = nameText.getText().toString();
            CountryCodePicker cpp = findViewById(R.id.cpp);
            String phoneNumber = cpp.getSelectedCountryCodeWithPlus() + phonetext.getText().toString().trim();


            if (username.isEmpty()) {
                nameText.setError("Please enter your name");
            } else if (phoneNumber.isEmpty()) {
                phonetext.setError("Please enter your phone number");
            } else if (!PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)) {
                Toast.makeText(this, "Please enter country code", Toast.LENGTH_LONG).show();
            } else {
                phoneLogin.init(SignUp.this, phoneNumber);
                phoneLogin.startPhoneNumberVerification(phoneNumber, SignUp.this);
            }
        });


        imgPicker.setOnClickListener(v -> {
            if (PermissionHandler.checkPermissions(this)) {
                pickImage();
            }else {
            pickImage();
            }
        });


    }



    private void pickImage() {
        pickImageLauncher.launch("image/*");
    }

    public ActivityResultLauncher<String> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if( result != null){
                profileImage.setImageURI(result);
                Dialog dialog = new Dialog(SignUp.this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
                dialog.setContentView(R.layout.crop_image);
                CropImageView imagaCropper = (CropImageView) dialog.findViewById(R.id.cropImageView);
                imagaCropper.setImageUriAsync(result);
                dialog.show();
                Button button = dialog.findViewById(R.id.cropButton);
                button.setOnClickListener(v -> {
                    Bitmap croppedImage = imagaCropper.getCroppedImage();
                    profileImage.setImageBitmap(croppedImage);
                    dialog.hide();
                });
            }
        }
    });
}