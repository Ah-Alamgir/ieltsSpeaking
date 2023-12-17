package com.ielts.speaking.publicClasses;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class PermissionHandler extends AppCompatActivity {


    public static final int PERMISSION_REQUEST_CODE = 1;


    public static String[] REQUEST_PERMISSION_OVER_32 = {
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,

            Manifest.permission.READ_MEDIA_IMAGES
    };
    public static String[] REQUEST_PERMISSION_UNDER_32 = {
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.INTERNET,
            Manifest.permission.CAMERA,

            Manifest.permission.READ_EXTERNAL_STORAGE
    };


    public static boolean checkPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            for(String permission: REQUEST_PERMISSION_OVER_32){
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED);
                return false;
            }
        } else {
            for(String permission : REQUEST_PERMISSION_UNDER_32){
                if(ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }

        return true;
    }

    public static void requestPermissions(Activity activity) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            ActivityCompat.requestPermissions(activity, REQUEST_PERMISSION_OVER_32, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(activity, REQUEST_PERMISSION_UNDER_32, PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}