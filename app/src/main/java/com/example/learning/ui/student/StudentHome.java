package com.example.learning.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.adpter.RecyclerViewCourse;
import com.example.learning.databinding.ActivityHomeStuentBinding;
import com.example.learning.model.CourseModel;
import com.example.learning.model.MemberModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentHome extends AppCompatActivity {

    ActivityHomeStuentBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    RecyclerViewCourse recyclerViewCourse=new RecyclerViewCourse();

    ArrayList list =new ArrayList<CourseModel>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityHomeStuentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnAddMember.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = binding.editId.getText().toString();
                        getCourses(id);
                        ref.child(Constants.course_path).child(id).child("members").child(Constants.User.getUid()).setValue(
                                new MemberModel(0,0,Constants.User.getEmail(),Constants.User.getUid())
                        );
                    }
                }
        );


        super.onCreate(savedInstanceState);
    }

    private void getCourses(String id ){
        ref.child(Constants.course_path).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(id)){
                            list.clear();
                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                list.add(snapshot1.getValue(CourseModel.class));
                            }
                            recyclerViewCourse.setList(list);
                            binding.recyclerViewHomeStudent.setAdapter(recyclerViewCourse);
                            recyclerViewCourse.setOnClick(
                                    new RecyclerViewCourse.OnClick() {
                                        @Override
                                        public void onItemClick(String id, String name) {
                                            Intent intent= new Intent(StudentHome.this, StudentDetails.class);
                                            intent.putExtra("courseId",id);
                                            intent.putExtra("courseName",name);
                                            startActivity(intent);

                                        }
                                    }
                            );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}
