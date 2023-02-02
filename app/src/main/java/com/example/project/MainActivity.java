package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    CardView uploadNotice,addGalleryImage,addTimetable,addFaculty,deleteNotice,deleteTimetable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadNotice = findViewById(R.id.addNotice);
        addGalleryImage = findViewById(R.id.addGalleryImage);
        addTimetable = findViewById(R.id.addTimetable);
        addFaculty=findViewById(R.id.addFaculty);
        deleteNotice=findViewById(R.id.deleteNotice);
        deleteTimetable=findViewById(R.id.deleteTimetable);



        uploadNotice.setOnClickListener(this);
        addGalleryImage.setOnClickListener(this);
        addTimetable.setOnClickListener(this);
        addFaculty.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
        deleteTimetable.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()){
            case R.id.addNotice:
                intent= new Intent(MainActivity.this,UploadNotice.class);
                startActivity(intent);
                break;
            case R.id.addGalleryImage:
                intent= new Intent(MainActivity.this,UploadImage.class);
                startActivity(intent);
                break;
            case R.id.addTimetable:
                intent= new Intent(MainActivity.this, UploadTimetable.class);
                startActivity(intent);
                break;
            case R.id.addFaculty:
                intent= new Intent(MainActivity.this, updateFaculty.class);
                startActivity(intent);
                break;
            case R.id.deleteTimetable:
                intent= new Intent(MainActivity.this, DeleteTimetableActivity.class);
                startActivity(intent);
                break;
            case R.id.deleteNotice:
                intent= new Intent(MainActivity.this, DeleteNoticeActivity.class);
                startActivity(intent);
                break;


        }
        }

    }
