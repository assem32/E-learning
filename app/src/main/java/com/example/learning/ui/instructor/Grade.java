package com.example.learning.ui.instructor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.adpter.RecyclerViewGrade;
import com.example.learning.databinding.ActivityInstuctorGradeBinding;
import com.example.learning.model.MemberModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Grade extends AppCompatActivity {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    RecyclerViewGrade recyclerViewGrade=new RecyclerViewGrade();

    ArrayList <MemberModel>memberModel=new ArrayList<>();
    ActivityInstuctorGradeBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityInstuctorGradeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String courseId=intent.getStringExtra("courseId");




        getGradeData(courseId);


    }


    private  void getGradeData(String courseId){
        ref.child(Constants.course_path)
                .child(courseId)
                .child("members")
                .addValueEventListener(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot snapshot1:snapshot.getChildren()){
                                    memberModel.add(snapshot1.getValue(MemberModel.class));
                                }
                                recyclerViewGrade.setList(memberModel);
                                binding.recyclerViewHomeStudent.setAdapter(recyclerViewGrade);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );
    }
}
