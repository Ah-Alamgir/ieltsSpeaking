package com.ielts.speaking.publicClasses;

import android.content.ContentValues;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ielts.speaking.homePage;

import java.sql.Struct;
import java.util.ArrayList;

public class fireBAse {

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

    
}
