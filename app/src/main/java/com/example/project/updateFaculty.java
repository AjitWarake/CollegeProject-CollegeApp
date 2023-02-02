package com.example.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class updateFaculty extends AppCompatActivity {
    FloatingActionButton fab;
    private RecyclerView mcaDepartment;
    private LinearLayout mcaNoData;
    private List<TeacherData> list1;
    private TeacherAdapter adapter;


    private DatabaseReference reference,dbRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);


        mcaDepartment=findViewById(R.id.mcaDepartment);
        //mbaDepartment=findViewById(R.id.mbaDepartment);
       // mscDepartment=findViewById(R.id.mscDepartment);
       // otherDepartment=findViewById(R.id.otherDepartment);

        mcaNoData=findViewById(R.id.mcaNoData);
       // mbaNoData=findViewById(R.id.mbaNoData);
      //  mscNoData=findViewById(R.id.mscNoData);
      //  otherNoData=findViewById(R.id.otherNoData);

        reference= FirebaseDatabase.getInstance().getReference().child("Teacher");



        mcaDepartment();
      //  mbaDepartment();
      //  mscDepartment();
      //  otherDepartment();



        fab=findViewById(R.id.fab);

        fab.setOnClickListener(v -> startActivity(new Intent(updateFaculty.this, addFaculty.class)));

    }

    private void mcaDepartment()   {
        dbRef=reference.child("catagory");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    mcaNoData.setVisibility(View.VISIBLE);
                    mcaDepartment.setVisibility(View.GONE);
                }else{
                    mcaNoData.setVisibility(View.GONE);
                    mcaDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    mcaDepartment.setHasFixedSize(true);
                    mcaDepartment.setLayoutManager(new LinearLayoutManager(updateFaculty.this));
                    adapter= new TeacherAdapter(list1,updateFaculty.this,"catagory");
                    mcaDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
    private void mbaDepartment() {
        dbRef=reference.child("MBA");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    mbaNoData.setVisibility(View.VISIBLE);
                    mbaDepartment.setVisibility(View.GONE);
                }else{
                    mbaNoData.setVisibility(View.GONE);
                    mbaDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    mbaDepartment.setHasFixedSize(true);
                    mbaDepartment.setLayoutManager(new LinearLayoutManager(updateFaculty.this));
                    adapter=new TeacherAdapter(list2,updateFaculty.this,"MBA");
                    mbaDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mscDepartment() {
        dbRef=reference.child("MSC");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    mscNoData.setVisibility(View.VISIBLE);
                    mscDepartment.setVisibility(View.GONE);
                }else{
                    mscNoData.setVisibility(View.GONE);
                    mscDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    mscDepartment.setHasFixedSize(true);
                    mscDepartment.setLayoutManager(new LinearLayoutManager(updateFaculty.this));
                    adapter=new TeacherAdapter(list3,updateFaculty.this,"MSC");
                    mscDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void otherDepartment() {
        dbRef=reference.child("Other");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4= new ArrayList<>();
                if(!dataSnapshot.exists()){
                    otherNoData.setVisibility(View.VISIBLE);
                    otherDepartment.setVisibility(View.GONE);
                }else{
                    otherNoData.setVisibility(View.GONE);
                    otherDepartment.setVisibility(View.VISIBLE);
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data= snapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    otherDepartment.setHasFixedSize(true);
                    otherDepartment.setLayoutManager(new LinearLayoutManager(updateFaculty.this));
                    adapter=new TeacherAdapter(list4,updateFaculty.this,"Other");
                    otherDepartment.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(updateFaculty.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


 */
    }


