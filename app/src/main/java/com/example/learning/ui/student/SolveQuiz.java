package com.example.learning.ui.student;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.databinding.ActivitySolveQuizBinding;
import com.example.learning.model.QuizModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SolveQuiz extends AppCompatActivity {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    ArrayList<QuizModel> listQuiz=new ArrayList<>();

    int currentIndex=0;

    int correctAnswer;

    double score=0;

    ActivitySolveQuizBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = ActivitySolveQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String courseId=intent.getStringExtra("courseId");
        String quizId=intent.getStringExtra("quizId");
        getQuiz(courseId,quizId);



//
//        binding.questionContentInSolveQuze.setText(listQuiz.get(currentIndex).getQuestion());


//        if(currentIndex <listQuiz.size()-1) {
//            binding.buttonConfermToSolveNextQuestion.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            currentIndex++;
//
//                            binding.questionContentInSolveQuze.setText(listQuiz.get(currentIndex).getQuestion());
//                        }
//                    }
//            );
//        }
//        else{
//            binding.buttonConfermToSolveNextQuestion.setOnClickListener(
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            currentIndex++;
//                        }
//                    }
//            );
//        }

    }

    private void  getQuiz(String courseId,String quizId){
        ArrayList<QuizModel> list=new ArrayList<>();

        ref.child(Constants.quiz_path).child(courseId).child(quizId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot snapshot1 :snapshot.getChildren()){
                            QuizModel quizModel= snapshot1.getValue(QuizModel.class);
                            list.add(snapshot1.getValue(QuizModel.class));
                        }
                        updateQuestion(list,currentIndex);

                        chooseCorrectAnswer();



                            binding.buttonConfermToSolveNextQuestion.setOnClickListener(
                                    new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(currentIndex<list.size()-1) {
                                                if(correctAnswer==list.get(currentIndex).getRightAnswer()){
                                                    score++;
                                                    Log.d("score", String.valueOf(score));
                                                }
                                            updateIndex();
                                            updateQuestion(list, currentIndex);
                                            correctAnswer=0;

                                            }
                                            else{
                                                binding.buttonConfermToSolveNextQuestion.setText("Upload answer");
                                                ref.child(Constants.answer_path).child(courseId).child(quizId).child(Constants.User.getUid()).child("Score").setValue(
                                                        score
                                                ).addOnSuccessListener(
                                                        new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(SolveQuiz.this,"Quiz submitted",Toast.LENGTH_SHORT).show();
                                                                ref.child(Constants.course_path)
                                                                        .child(courseId)
                                                                        .child("members")
                                                                        .child(Constants.User.getUid())
                                                                        .child("quizGrade")
                                                                        .addListenerForSingleValueEvent(
                                                                                new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                                        double quizDegree=snapshot.getValue(Double.class);
                                                                                        ref.child(Constants.course_path)
                                                                                                .child(courseId)
                                                                                                .child("members")
                                                                                                .child(Constants.User.getUid())
                                                                                                .child("quizGrade")
                                                                                                .setValue(quizDegree+score);
                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                                                    }
                                                                                }
                                                                        );
                                                            }
                                                        }
                                                );
                                            }
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

    private void updateIndex(){
        currentIndex++;
    }
    private void updateQuestion(@NonNull ArrayList <QuizModel>list, int currentIndex ){
        binding.questionContentInSolveQuze.setText(list.get(currentIndex).getQuestion());
        binding.chooseOneInSolveQuze.setText(list.get(currentIndex).getAnswer1());
        binding.chooseTwoInSolveQuze.setText(list.get(currentIndex).getAnswer2());
        binding.chooseThreeInSolveQuze.setText(list.get(currentIndex).getAnswer3());
        binding.chooseFoureInSolveQuze.setText(list.get(currentIndex).getAnswer4());
    }
    private void chooseCorrectAnswer(){
        binding.chooseOneInSolveQuze.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correctAnswer=1;
                        binding.chooseOneInSolveQuze.setBackgroundColor(0xff808080);
                        binding.chooseTwoInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseThreeInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseFoureInSolveQuze.setBackgroundColor(0xffffffff);
                    }
                }
        );


        binding.chooseTwoInSolveQuze.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correctAnswer=2;
                        binding.chooseOneInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseTwoInSolveQuze.setBackgroundColor(0xff808080);
                        binding.chooseThreeInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseFoureInSolveQuze.setBackgroundColor(0xffffffff);
                    }
                }
        );
        binding.chooseThreeInSolveQuze.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correctAnswer=3;
                        binding.chooseOneInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseTwoInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseThreeInSolveQuze.setBackgroundColor(0xff808080);
                        binding.chooseFoureInSolveQuze.setBackgroundColor(0xffffffff);
                    }
                }
        );

        binding.chooseFoureInSolveQuze.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        correctAnswer=4;
                        binding.chooseOneInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseTwoInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseThreeInSolveQuze.setBackgroundColor(0xffffffff);
                        binding.chooseFoureInSolveQuze.setBackgroundColor(0xff808080);
                    }
                }
        );
    }
}
