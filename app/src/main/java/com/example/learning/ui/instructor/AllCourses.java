package com.example.learning.ui.instructor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.adpter.RecyclerViewCourse;
import com.example.learning.databinding.ActivityAllCoursesBinding;
import com.example.learning.model.CourseModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllCourses extends AppCompatActivity {
    ActivityAllCoursesBinding binding;
    RecyclerViewCourse recyclerViewCourse=new RecyclerViewCourse();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth =FirebaseAuth.getInstance();

    String id= auth.getUid();

    ArrayList list =new ArrayList<CourseModel>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityAllCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

//        getData();

        getCourses();

        binding.btnAddCourse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(AllCourses.this, AddCourse.class));
                    }
                }
        );
    }




    public  void getCourses(){
        ref.child(Constants.course_path).orderByChild("instructorId").equalTo(auth.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 :snapshot.getChildren()){
                            list.add(snapshot1.getValue(CourseModel.class));
                        }
//                        for(DataSnapshot snapshot1:snapshot.getChildren()){
//                            for (DataSnapshot snapshot2:snapshot1.getChildren()) {
//                                if(snapshot2.hasChild("instructorId"))
//
//                                 {
//                                     String id = snapshot2.getChildren().toString();
//                                     if(id== auth.getUid()) {
//                                         System.out.println("try");
//                                         list.add(snapshot1.getValue(CourseModel.class));
//                                     }
//                                }
//                            }





//                        for (DataSnapshot snapshot1:snapshot.getChildren()){
//                            for (DataSnapshot snapshot2:snapshot1.getChildren())
//                                if(snapshot2.hasChild(Constants.User.getUid()))
//                                    list.add(snapshot1.getValue(CourseModel.class));
//                        }
                        recyclerViewCourse.setList(list);

                        binding.recyclerInstructorCourse.setAdapter(recyclerViewCourse);

                        recyclerViewCourse.setOnClick(
                                new RecyclerViewCourse.OnClick() {
                                    @Override
                                    public void onItemClick(String id, String name) {
                                        Intent intent =new Intent(AllCourses.this,CourseDetails.class);
                                        intent.putExtra("courseId",id);
                                        intent.putExtra("courseName",name);

                                        Log.d("id list",id);
                                        startActivity(intent);


//                                        Toast.makeText(AllCourses.this,name,Toast.LENGTH_SHORT).show();
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

