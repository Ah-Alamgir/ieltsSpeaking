package com.ielts.speaking.publicClasses;

import android.content.ContentValues;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ielts.speaking.homePage;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Objects;

public class fireBAse {

    public static String phoneNumber = "+88016458246655";
    public static boolean createFirebase(String phone, String name, String imageUrl){
        final boolean[] isComplete = {false};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        ContentValues values = new ContentValues();
        values.put("NAME", name);
        values.put("ACTIVE", true);
        values.put("IMAGE_PATH", imageUrl);
        databaseReference.child(phone).setValue(values).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                isComplete[0] =true;
            }

        });

        return isComplete[0];
    }


    public static ArrayList<String> activeStatus(ArrayList<String> numberInHistory){

        ArrayList<String> activeList = new ArrayList<String>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        for(int i = 0; i < numberInHistory.size(); i++){
            databaseReference.child(numberInHistory.get(i)).child("ACTIVE").get().addOnCompleteListener(task -> {
                String received = task.getResult().toString();
                Log.d(homePage.TAG, received);
            });
        }
        return activeList;
    }

    public static ArrayList<String> getMessages(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Messages");
        ArrayList<String> messages = new ArrayList<String>();
        try {
            phoneNumber = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getPhoneNumber();
//            String phoneNumer = ;
//
//            assert phoneNumer != null;
            databaseReference.child("+8801872472787").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot snapshots: snapshot.getChildren()) {
                        Log.d(homePage.TAG, String.valueOf(snapshots));
                    };
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){};

        return messages;
    }


    
}
