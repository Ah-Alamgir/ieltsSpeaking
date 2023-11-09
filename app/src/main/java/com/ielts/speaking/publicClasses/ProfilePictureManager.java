package com.ielts.speaking.publicClasses;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfilePictureManager {

    private static final int REQUEST_PICK_IMAGE = 101;
    private static final int REQUEST_CROP_IMAGE = 102;
    private static final String TAG = "ProfilePictureManager";
    private static final String FILE_PROVIDER_AUTHORITY = "com.your.package.name.fileprovider";

    private final Activity activity;
    private Uri imageUri;

    public ProfilePictureManager(Activity activity) {
        this.activity = activity;
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            activity.startActivityForResult(intent, REQUEST_PICK_IMAGE);
        } catch (ActivityNotFoundException e) {
            // Handle no activity found to handle the image pick intent
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                startCropImage(selectedImageUri);
            }
        } else if (requestCode == REQUEST_CROP_IMAGE && resultCode == Activity.RESULT_OK) {
            handleCropImage();
        }
    }

    private void startCropImage(Uri sourceUri) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";

        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File outputDir = new File(storageDir, "ProfilePictures");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputFile = new File(outputDir, fileName);
        imageUri = FileProvider.getUriForFile(activity, FILE_PROVIDER_AUTHORITY, outputFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(sourceUri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 512);
        intent.putExtra("outputY", 512);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);

        try {
            activity.startActivityForResult(intent, REQUEST_CROP_IMAGE);
        } catch (ActivityNotFoundException e) {
            // Handle no activity found to handle the crop intent
        }
    }

    private void handleCropImage() {
        if (imageUri != null) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
                saveImageToFile(bitmap);
            } catch (IOException e) {
                Log.e(TAG, "Error while saving cropped image: " + e.getMessage());
                // Handle error while saving the cropped image
            }
        }
    }

    private void saveImageToFile(Bitmap bitmap) throws IOException {
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File outputDir = new File(storageDir, "ProfilePictures");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName = "ProfilePic_" + timeStamp + ".jpg";
        File outputFile = new File(outputDir, fileName);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
        outputStream.flush();
        outputStream.close();

        // The cropped image is saved as outputFile
        // You can use this file path to set it as a profile picture or perform any other action
        String imagePath = outputFile.getAbsolutePath();

        // TODO: Perform actions with the imagePath (e.g., set it as a profile picture)
    }
}
