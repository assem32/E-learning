package com.example.learning.ui.instructor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.MainActivity;
import com.example.learning.databinding.ActivityCourseDetailsBinding;
import com.example.learning.databinding.ActivityCreateCourseBinding;
import com.example.learning.model.CourseModel;
import com.example.learning.model.UserModel;
import com.example.learning.ui.auth.LoginScreen;
import com.example.learning.ui.auth.SignUp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCourse extends AppCompatActivity {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ActivityCreateCourseBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        binding.btnCreateCourse.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = binding.editCourseName.getText().toString();
                        String quizGrade=binding.editQuizGrade.getText().toString();
                        String projectGrade=binding.editProjectGrade.getText().toString();
                        String attendanceGrade=binding.editAttendGrade.getText().toString();
                        validate(name,quizGrade,projectGrade,attendanceGrade);
                    }
                }
        );


    }

    private void validate(String name ,String quizGrade ,String projectGrade ,String attendanceGrade){
        if (name.isEmpty()){
            Toast.makeText(AddCourse.this,"Name is empty",Toast.LENGTH_SHORT).show();

        }
        else if (quizGrade.isEmpty()){
            Toast.makeText(AddCourse.this,"Quiz Grade is empty",Toast.LENGTH_SHORT).show();

        }
        else if (projectGrade.isEmpty()){
            Toast.makeText(AddCourse.this,"Project Grade is empty",Toast.LENGTH_SHORT).show();

        }
        else if (attendanceGrade.isEmpty()){
            Toast.makeText(AddCourse.this,"Attendance Grade is empty",Toast.LENGTH_SHORT).show();

        }
        else
            addCourseToRealTime(name, Double.parseDouble(quizGrade),Double.parseDouble(attendanceGrade),Double.parseDouble(projectGrade));

    }

    private void addCourseToRealTime(String courseName,double assignmentGrade,double attendanceGrade,double projectGrade){
        String id = ref.push().getKey();
        ref.child(Constants.course_path).child(id).setValue(new CourseModel(LoginScreen.auth.getUid(),id,courseName,assignmentGrade,attendanceGrade,projectGrade)).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity( new Intent(AddCourse.this, AllCourses.class));
                        finish();
                        Toast.makeText(AddCourse.this,"Added successfully",Toast.LENGTH_SHORT).show();
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCourse.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
