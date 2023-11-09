package com.ielts.speaking.publicClasses;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AirtableApiClient {

    private static final String PERSONAL_ACCESS_TOKEN = "patyMeNO7jQrQvhfF.66d0880c76c8a939af4156d478eba508b5b827d3e1ebbf5bec62397e13efb4b9";

    private final OkHttpClient client;

    public AirtableApiClient() {
        client = new OkHttpClient();
    }

    private static final String TAG = "AirtableApiClient";

    public void fetchRecords(Context context) {

        SqliteDataHelp sqliteDataHelp = new SqliteDataHelp(context);
        Request request = new Request.Builder()
                .url("https://api.airtable.com/v0/appxDx9zrl1hDanNW/list")
                .addHeader("Authorization", "Bearer " + PERSONAL_ACCESS_TOKEN)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Handle failure
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();


                    try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        for(int i =0; i < jsonObject.getJSONArray("records").length(); i++){


                            ContentValues contentValues = new ContentValues();
                            contentValues.put(sqliteDataHelp.COLUMN_1, jsonObject.getJSONArray("records").getJSONObject(i).getJSONObject("fields").getString("Answer 1"));
                            contentValues.put(sqliteDataHelp.COLUMN_2, jsonObject.getJSONArray("records").getJSONObject(i).getJSONObject("fields").getString("Answer 2"));
                            contentValues.put(sqliteDataHelp.COLUMN_3, jsonObject.getJSONArray("records").getJSONObject(i).getJSONObject("fields").getString("Answer 3"));
                            contentValues.put(sqliteDataHelp.COLUMN_4, jsonObject.getJSONArray("records").getJSONObject(i).getJSONObject("fields").getString("Answer 4"));
                            contentValues.put(sqliteDataHelp.BOOK_NAME, jsonObject.getJSONArray("records").getJSONObject(i).getJSONObject("fields").getString("Book Name"));

                            sqliteDataHelp.saveDataInSQLite(contentValues);
                        }


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }



                } else {
                    // Handle unsuccessful response
                    System.out.println(response.code() + " " + response.message());
                }
            }
        });
    }


}