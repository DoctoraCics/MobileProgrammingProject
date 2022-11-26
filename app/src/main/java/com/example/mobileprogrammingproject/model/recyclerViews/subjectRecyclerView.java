package com.example.mobileprogrammingproject.model.recyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobileprogrammingproject.model.Subject;
import java.util.ArrayList;

public class subjectRecyclerView extends RecyclerView.Adapter<subjectRecyclerView.subjectViewHolder> {
    Context context;
    ArrayList<Subject> subjectList;

    public subjectRecyclerView(Context context, ArrayList<Subject> retreivedSubjects){
        this.context = context;
        this.subjectList = retreivedSubjects;
    }

    @NonNull
    @Override
    public subjectRecyclerView.subjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        //View view = inflater.inflate(); //<--find layout created by jez
        //return new subjectRecyclerView.subjectViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull subjectRecyclerView.subjectViewHolder holder, int position) {

        //setText for components in the xml

    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class subjectViewHolder extends RecyclerView.ViewHolder{

        //Add Components from xml

        public subjectViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }
}
