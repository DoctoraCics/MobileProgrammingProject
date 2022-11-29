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
import com.example.mobileprogrammingproject.model.Student;
import java.util.ArrayList;

public class studentUnderSubjectRecycler extends RecyclerView.Adapter<studentUnderSubjectRecycler.studentViewHolder> {

    Context context;
    ArrayList<Student> studentList;
    int subject_Id;
    private final RecyclerInterface recyclerInterface;

    public studentUnderSubjectRecycler(Context context,
                                       ArrayList<Student> retreivedStudents,
                                       int subject_Id,
                                       RecyclerInterface recyclerInterface){
        this.subject_Id = subject_Id;
        this.context = context;
        this.studentList = retreivedStudents;
        this.recyclerInterface = recyclerInterface;
    }

    @NonNull
    @Override
    public studentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_student, parent, false);
        return new studentUnderSubjectRecycler.studentViewHolder(view, recyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull studentViewHolder holder, int position) {
        holder.studentName.setText("Name: " + studentList.get(position).getStudent_Name());
        holder.studentGrade.setText(String.valueOf("Grade: " + studentList.get(position).getStudent_Grade()));
        holder.studentStatus.setText(String.valueOf("Status: " + studentList.get(position).getStudent_Status()));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public static class studentViewHolder extends RecyclerView.ViewHolder{

        private TextView studentName;
        private TextView studentGrade;
        private TextView studentStatus;
        private Button updateStudent;
        private Button unEnrollStudent;

        public studentViewHolder(@NonNull View itemView, RecyclerInterface recyclerInterface) {
            super(itemView);
            this.studentName = itemView.findViewById(R.id.studentdispName);
            this.studentGrade = itemView.findViewById(R.id.studentdispGrade);
            this.studentStatus = itemView.findViewById(R.id.studentdispStatus);
            this.updateStudent = itemView.findViewById(R.id.studentUpdate);
            this.unEnrollStudent = itemView.findViewById(R.id.studentDelete);

            updateStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerInterface.onUpdateClick(position);
                        }
                    }

                }
            });

            unEnrollStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerInterface != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            recyclerInterface.onDeleteCLick(position);
                        }
                    }
                }
            });
        }

    }
}
