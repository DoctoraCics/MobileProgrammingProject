package com.example.mobileprogrammingproject.model.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprogrammingproject.model.Subject;

import java.util.ArrayList;

public class studentUnderSubjectRecycler extends RecyclerView.Adapter<studentUnderSubjectRecycler.studentViewHolder> {
    Context context;
    ArrayList<Subject> studentList;
    int subject_Id;


    public studentUnderSubjectRecycler(Context context, ArrayList<Subject> retreivedStudents, int subject_Id){
        this.subject_Id = subject_Id;
        this.context = context;
        this.studentList = retreivedStudents;
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        //View view = inflater.inflate(); //<--find layout created by jez
        //return new studentUnderSubjectRecycler.studentViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewHolder holder, int position) {

        //setText for components in the xml

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class studentViewHolder extends RecyclerView.ViewHolder{

        //Add Components from xml

        public studentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
