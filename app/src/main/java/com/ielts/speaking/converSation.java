package com.ielts.speaking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.ielts.speaking.publicClasses.fireBAse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class converSation extends AppCompatActivity {
private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Messages");
private String sendingTo = "+8801872472787";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conver_sation);

    }



    private void formatMessage(String message){

        JSONObject messageJson = new JSONObject();

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String utcTime = sdf.format(new Date());
            String messageId = UUID.randomUUID().toString();
            messageJson.put("message", message);
            messageJson.put("sendingTime", utcTime);
            messageJson.put("messageId", messageId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String messageJsonString = messageJson.toString();
        if(!messageJsonString.isEmpty()){
            databaseReference.child(sendingTo).child(fireBAse.phoneNumber).push().setValue(messageJsonString).addOnFailureListener(e -> Toast.makeText(converSation.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        }

    }


    private void getMessage(){
        databaseReference.child(fireBAse.phoneNumber).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}