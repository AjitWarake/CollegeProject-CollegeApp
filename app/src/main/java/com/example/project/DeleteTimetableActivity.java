package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteTimetableActivity extends AppCompatActivity {

    private RecyclerView deleteTimetableRecycler;
    private ProgressBar progressBar;
    private ArrayList<TimetableData> list;
    private TimetableAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_timetable);

        deleteTimetableRecycler=findViewById(R.id.deleteTimetableRecycler);
        progressBar=findViewById(R.id.progressBar);
        reference= FirebaseDatabase.getInstance().getReference().child("Timetable");


        deleteTimetableRecycler.setLayoutManager(new LinearLayoutManager(this));
        deleteTimetableRecycler.setHasFixedSize(true);


        getTimetable();


    }



    private void getTimetable() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list=new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    TimetableData data= snapshot.getValue(TimetableData.class);
                    list.add(data);
                }

                adapter=new TimetableAdapter(DeleteTimetableActivity.this,list);

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                deleteTimetableRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DeleteTimetableActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}