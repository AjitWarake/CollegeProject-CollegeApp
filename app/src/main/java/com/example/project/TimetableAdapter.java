package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.TimetableViewAdapter> {

    private Context context;
    private ArrayList<TimetableData> list;

    public TimetableAdapter(Context context, ArrayList<TimetableData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TimetableAdapter.TimetableViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.timetable_item_layout,parent,false);

        return new TimetableAdapter.TimetableViewAdapter(view);
    }



    @Override
    public void onBindViewHolder(@NonNull TimetableAdapter.TimetableViewAdapter holder, int position) {

        TimetableData currentItem = list.get(position);

        holder.deleteTimetableTitle.setText(currentItem.getTitle());

        try {
            if (currentItem.getImage() != null)
                Picasso.get().load(currentItem.getImage()).into(holder.deleteTimetableImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.deleteTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Timetable");
                reference.child(currentItem.getKey()).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        });
                notifyItemRemoved(position);


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TimetableViewAdapter extends RecyclerView.ViewHolder {
        private Button deleteTimetable;
        private TextView deleteTimetableTitle;
        private ImageView deleteTimetableImage;

        public TimetableViewAdapter(@NonNull View itemView) {
            super(itemView);
            deleteTimetable=itemView.findViewById(R.id.deleteTimetable);
            deleteTimetableTitle=itemView.findViewById(R.id.deleteTimetableTitle);
            deleteTimetableImage=itemView.findViewById(R.id.deleteTimetableImage);
        }
    }


}
