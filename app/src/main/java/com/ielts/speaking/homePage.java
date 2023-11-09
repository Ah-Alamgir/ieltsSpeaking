package com.ielts.speaking;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ielts.speaking.publicClasses.AirtableApiClient;
import com.ielts.speaking.publicClasses.SqliteDataHelp;
import com.ielts.speaking.publicClasses.fireBAse;

import java.util.ArrayList;

public class homePage extends AppCompatActivity {
 TabLayout tabLayout;
 ViewPager2 viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        phoneLogin.onActivityStart(homePage.this);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        createTabLayout();
        getQuestion();

    }



public static String TAG = "AirtableApiClient";
    private void getQuestion(){
        SqliteDataHelp sqliteDataHelp = new SqliteDataHelp(this);
        if((sqliteDataHelp.isDatabaseEmpty()==0)){
            AirtableApiClient airtableApiClient = new AirtableApiClient();
            airtableApiClient.fetchRecords(this);
        }

        createRecyclerViewOfChat();
    }

    private void createRecyclerViewOfChat(){
        ArrayList<String> numbers = new ArrayList<String>();
        numbers.add("+8801872472787");
        fireBAse.activeStatus(numbers);
    }


























    private void createTabLayout() {
        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                // Return the appropriate fragment based on the position
                switch (position) {
                    case 0:
                        return new calllerList();
                    case 1:
                        return new camBridge();
                    case 2:
                        return new globalCall();
                }
                return null;
            }

            @Override
            public int getItemCount() {
                // Return the total number of fragments
                return 3;
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Set the tab text or icons as per your requirement
            switch (position) {
                case 0:
                    tab.setText("Friends");
                    break;
                case 1:
                    tab.setText("Practice");
                    break;
                case 2:
                    tab.setText("Global");
                    break;
            }
        }).attach();
    }

}