package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadTimetable extends AppCompatActivity {
    private CardView addimage;
    private final int REQ=1;
    private Bitmap bitmap;
    private ImageView timetableImageView;


    private Button uploadTimetableBtn;

    private EditText timetableTitle;

    private DatabaseReference reference,dbRef;
    private StorageReference storageReference;
    String downloadUrl="";
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_timetable);

        reference= FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        pd=new ProgressDialog(this);

        addimage=findViewById(R.id.addImage);

        timetableImageView=findViewById(R.id.timetableImageView);
        timetableTitle=findViewById(R.id.timetableTitle);
        uploadTimetableBtn = findViewById(R.id.uploadTimetableBtn);

        addimage.setOnClickListener((view -> {openGallery();}));

        uploadTimetableBtn.setOnClickListener(view -> {
            if(timetableTitle.getText().toString().isEmpty()){
                timetableTitle.setError("Empty");
                timetableTitle.requestFocus();
            } else if(bitmap==null){
                uploadData();
            }else {
                uploadImage();
            }
        });

    }


    private void uploadImage() {

        pd.setMessage("Uploading...");
        pd.show();

        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalimg = baos.toByteArray();

        final StorageReference filePath;

        filePath= storageReference.child("Timetable").child(finalimg+"jpg");

        final UploadTask uploadTask= filePath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UploadTimetable.this, task -> {
            if(task.isSuccessful()) {
                uploadTask.addOnSuccessListener(taskSnapshot -> filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                    downloadUrl = String.valueOf(uri);
                    uploadData();

                }));


            }else{
                pd.dismiss();


                Toast.makeText(this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }


        });





    }

    private void uploadData() {
        dbRef=reference.child("Timetable");
        final String uniqueKey= dbRef.push().getKey();

        String title= timetableTitle.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");

        String date= currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime= new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        TimetableData timetableData = new TimetableData(title,downloadUrl,date,time,uniqueKey);

        dbRef.child(uniqueKey).setValue(timetableData).addOnSuccessListener(aVoid -> {
            pd.dismiss();
            Toast.makeText(UploadTimetable.this, "Timetable Uploaded", Toast.LENGTH_SHORT).show();

        }).addOnFailureListener(e -> {
            pd.dismiss();
            Toast.makeText(UploadTimetable.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        });



    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode == RESULT_OK){
            Uri uri= data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            timetableImageView.setImageBitmap(bitmap);

        }
    }


}