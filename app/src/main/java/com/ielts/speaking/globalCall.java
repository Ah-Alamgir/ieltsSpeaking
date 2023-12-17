package com.ielts.speaking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ielts.speaking.publicClasses.SqliteDataHelp;

public class globalCall extends Fragment {
public Context context;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context  = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.context.startActivity(new Intent(this.context,  VoiceChatViewActivity.class));

        return inflater.inflate(R.layout.fragment_global_call, container, false);
    }
}