package com.example.learning.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.adpter.RecyclerViewCourse;
import com.example.learning.adpter.RecyclerViewQuiz;
import com.example.learning.databinding.ActivityAllCourseQuizBinding;
import com.example.learning.model.QuizModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizsPage extends AppCompatActivity {
    ActivityAllCourseQuizBinding binding;

    ArrayList <String> QuizId=new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    RecyclerViewQuiz recyclerViewQuiz=new RecyclerViewQuiz();

    ArrayList <QuizModel> list =new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityAllCourseQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String id=intent.getStringExtra("courseId");

        getQuizes(id);

    }

    private void getQuizes(String id){
        ref.child(Constants.quiz_path).child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                            System.out.println("hi");
                            for(DataSnapshot snapshot1:snapshot.getChildren()){
                                list.add(snapshot.getValue(QuizModel.class));
                                QuizId.add(snapshot1.getKey());
                                System.out.println("hi");
                            }


                        recyclerViewQuiz.setList(list);
                        binding.recyclerStudentQuiz.setAdapter(recyclerViewQuiz);
                        recyclerViewQuiz.setOnClick(
                                new RecyclerViewQuiz.OnClickQuiz() {
                                    @Override
                                    public void onItemClick(int index) {
                                        String qId=QuizId.get(index);
                                        Intent intent = new Intent(QuizsPage.this, SolveQuiz.class);
                                        intent.putExtra("courseId",id);
                                        intent.putExtra("quizId",qId);
                                        startActivity(intent);

                                    }
                                }
                        );


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}
