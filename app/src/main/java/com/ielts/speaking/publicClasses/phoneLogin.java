package com.ielts.speaking.publicClasses;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.ielts.speaking.R;
import com.ielts.speaking.SignUp;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class phoneLogin {
    private static final String TAG = "PhoneAuthActivity";
    private static FirebaseAuth mAuth;


    private static String mVerificationId;
    private static PhoneAuthProvider.ForceResendingToken mResendToken;
    private static PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public static AlertDialog alertDialog;



    public static void onActivityStart(Activity activity) {

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            activity.startActivity(new Intent(activity, SignUp.class));
        }
    }

    public static void init(Activity activity, String phone){
        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

                alertDialog.dismiss();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                alertDialog.dismiss();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
                dialogBuilder.setTitle("Verification Failed");
                dialogBuilder.setMessage(e.getMessage());
                alertDialog = dialogBuilder.create();
                alertDialog.show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
                alertDialog.dismiss();
                showDialog(activity, phone);
            }
        };
    }


    public static void startPhoneNumberVerification(String phoneNumber, Activity activity) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        loading(activity);
    }


    public static void verifyPhoneNumberWithCode(String verificationId, String code, Activity activity) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential, activity );
        Log.d("keys", credential.toString());
    }

    // [START resend_verification]
    public static void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token, Activity activity) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(activity)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    public static void signInWithPhoneAuthCredential(PhoneAuthCredential credential, Activity activity) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        updateUI(user);
                        alertDialog.dismiss();
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private static void updateUI(FirebaseUser user) {


    }






    public static void showDialog(Activity activity, String phoneNumber){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        View dialogView = activity.getLayoutInflater().inflate(R.layout.verification, null);
        dialogBuilder.setView(dialogView);

        EditText editText = dialogView.findViewById(R.id.verific);
        TextView textView = dialogView.findViewById(R.id.textView6);
        Button submitButton = dialogView.findViewById(R.id.button);
        textView.setOnClickListener(v -> resendVerificationCode(phoneNumber,mResendToken, activity));


        submitButton.setOnClickListener(v -> {
            String userInput = editText.getText().toString().trim();
            if(userInput.isEmpty()) {
                editText.setError("Please enter the code");
            }else {
                verifyPhoneNumberWithCode(mVerificationId, userInput, activity);
            }
        });


        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    public static void loading(Activity activity){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setView(R.layout.loading);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


}
