package com.example.mobileprogrammingproject.model.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprogrammingproject.R;
import com.example.mobileprogrammingproject.model.RecyclerInterface;
import com.example.mobileprogrammingproject.model.Subject;
import java.util.ArrayList;

public class subjectRecyclerView extends RecyclerView.Adapter<subjectRecyclerView.subjectViewHolder> {

    Context context;
    ArrayList<Subject> subjectList;
    private final RecyclerInterface RecyclerInterface;

    public subjectRecyclerView(Context context, ArrayList<Subject> retreivedSubjects, RecyclerInterface RecyclerInterface){
        this.context = context;
        this.subjectList = retreivedSubjects;
        this.RecyclerInterface = RecyclerInterface;
    }

    @NonNull
    @Override
    public subjectRecyclerView.subjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reycler_view_subject, parent, false);
        return new subjectRecyclerView.subjectViewHolder(view, RecyclerInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull subjectRecyclerView.subjectViewHolder holder, int position) {
        holder.subjectdispName.setText(subjectList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class subjectViewHolder extends RecyclerView.ViewHolder{

        Button updateSubject;
        Button deleteSubject;
        TextView subjectdispName;

        public subjectViewHolder(@NonNull View itemView, RecyclerInterface RecyclerInterface) {
            super(itemView);
            this.updateSubject = itemView.findViewById(R.id.updateSubject);
            this.deleteSubject = itemView.findViewById(R.id.deleteSubject);
            this.subjectdispName = itemView.findViewById(R.id.subjectdispName);

            updateSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (RecyclerInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            RecyclerInterface.onUpdateClick(position);
                        }
                    }
                }
            });
            deleteSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (RecyclerInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            RecyclerInterface.onDeleteCLick(position);
                        }
                    }
                }
            });
        }

    }
}
