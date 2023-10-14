package com.example.learning.ui.instructor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.databinding.ActivityCreateQuizBinding;
import com.example.learning.model.QuizModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateQuiz extends AppCompatActivity {

    ActivityCreateQuizBinding binding;

    ArrayList list=new ArrayList<QuizModel>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();









    private int rightAnswer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityCreateQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        Intent intent =getIntent();
        String id =intent.getStringExtra("courseId");

        binding.checkBoxOne.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            rightAnswer = 1;
                            Log.d("rightAnswer",rightAnswer+"");
                            binding.checkBoxOne.setChecked(true);
                            binding.checkBoxTwo.setChecked(false);
                            binding.checkBoxThree.setChecked(false);
                            binding.checkBoxFour.setChecked(false);
                        }
                    }
                }
        );

        binding.checkBoxTwo.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            rightAnswer = 2;
                            Log.d("rightAnswer",rightAnswer+"");
                            binding.checkBoxTwo.setChecked(true);
                            binding.checkBoxThree.setChecked(false);
                            binding.checkBoxFour.setChecked(false);
                            binding.checkBoxOne.setClickable(false);
                        }
                    }
                }
        );

        binding.checkBoxThree.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            rightAnswer = 3;
                            Log.d("rightAnswer",rightAnswer+"");
                            binding.checkBoxTwo.setChecked(false);
                            binding.checkBoxThree.setChecked(true);
                            binding.checkBoxFour.setChecked(false);
                            binding.checkBoxOne.setClickable(false);
                        }
                    }
                }
        );
        binding.checkBoxFour.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            rightAnswer = 4;
                            Log.d("rightAnswer",rightAnswer+"");
                            binding.checkBoxTwo.setChecked(false);
                            binding.checkBoxThree.setChecked(false);
                            binding.checkBoxFour.setChecked(true);
                            binding.checkBoxOne.setClickable(false);
                        }
                    }
                }
        );

        binding.buttonMakeNextQuestion.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        validate();
                    }
                }
        );

        binding.btnUploadQuiz.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addQuiz(list,id);
                    }
                }
        );

    }

    private void validate(){
        String question = binding.editQuestion.getText().toString().trim();
        String answer1 = binding.editAnswer1.getText().toString().trim();
        String answer2 = binding.editAnswer2.getText().toString().trim();
        String answer3 = binding.editAnswer3.getText().toString().trim();
        String answer4 = binding.editAnswer4.getText().toString().trim();

        if(question.isEmpty()){
            Toast.makeText(CreateQuiz.this,"Enter a question",Toast.LENGTH_SHORT).show();
        }

        else if(answer1.isEmpty()){
            Toast.makeText(CreateQuiz.this,"Enter an answer1",Toast.LENGTH_SHORT).show();
        }

        else if(answer2.isEmpty()){
            Toast.makeText(CreateQuiz.this,"Enter an answer 2",Toast.LENGTH_SHORT).show();

        }
        else if(answer3.isEmpty()){
            Toast.makeText(CreateQuiz.this,"Enter an answer 3",Toast.LENGTH_SHORT).show();
        }
        else if(answer4.isEmpty()){
            Toast.makeText(CreateQuiz.this,"Enter an answer 4",Toast.LENGTH_SHORT).show();
        }
        else
        {
            list.add(new QuizModel(answer1,answer2,answer3,answer4,question,rightAnswer));

            binding.checkBoxOne.setChecked(false);
            binding.checkBoxTwo.setChecked(false);
            binding.checkBoxThree.setChecked(false);
            binding.checkBoxFour.setChecked(false);
            binding.editAnswer1.setText("");
            binding.editAnswer2.setText("");
            binding.editAnswer3.setText("");
            binding.editAnswer4.setText("");
            binding.editQuestion.setText("");
        }

    }

    private void addQuiz(ArrayList <QuizModel> list,String courseId){
        ref.child(Constants.quiz_path)
                .child(courseId)
                .push()
                .setValue(list)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(CreateQuiz.this,"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }
                ).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateQuiz.this,e.toString(),Toast.LENGTH_SHORT).show();

                            }
                        }
                );
    }


}
