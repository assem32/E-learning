package com.example.learning.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.databinding.ActivityStudentDetailsBinding;
import com.example.learning.model.MemberModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentDetails extends AppCompatActivity {
    ActivityStudentDetailsBinding binding;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        binding =ActivityStudentDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        String name =intent.getStringExtra("courseName");
        String id =intent.getStringExtra("courseId");


        binding.subjectName.setText(name);
        binding.studentName.setText(Constants.User.getName());

        binding.chatInStudentpage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1=new Intent(StudentDetails.this, Chat.class);
                        intent1.putExtra("courseId",id);
                        startActivity(intent1);
                    }
                }
        );


        binding.btnAttend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String code=binding.editAttend.getText().toString();
                        check(id,code);
                    }
                }
        );

        binding.btnSolveQuiz.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1=new Intent(StudentDetails.this, QuizsPage.class);
                        intent1.putExtra("courseId",id);
                        startActivity(intent1);
                    }
                }
        );


    }

    private void check(String id,String code){


        ref.child("Attendance").child(id).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(code)){
                            ref.child("Attendance").child(id).child(code).addValueEventListener(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.hasChild(Constants.User.getUid())){
                                                Toast.makeText(StudentDetails.this,"Already Attended",Toast.LENGTH_SHORT).show();

                                            }
                                            else {
                                                attend(id,code);
                                                attendMember(id);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    }
                            );
                        }
                        else {
                            Toast.makeText(StudentDetails.this,"No lecture with this code ",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }

    private void attendMember(String id){

        ref.child(Constants.course_path)
                .child(id)
                .child("members")
                .child(Constants.User.getUid())
                .child("attendanceGrade")
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                double degree = snapshot.getValue(Double.class);
                                ref.child(Constants.course_path)
                                        .child(id)
                                        .child("members")
                                        .child(Constants.User.getUid())
                                        .child("attendanceGrade")
                                        .setValue(degree+1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );
    }

    private void attend(String id,String code){
        ref.child("Attendance").child(id).child(code).child(Constants.User.getUid()).setValue(Constants.User.getUid()).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(StudentDetails.this,"Attended successfully",Toast.LENGTH_SHORT).show();
//                        setAttendance(id);
                    }
                }
        );
    }


    private void setAttendance(String id){

        ref.child(Constants.course_path)
                .child(id)
                .child("members")
                .child(Constants.User.getUid())
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if (snapshot.hasChild("attendance")){
                                   for(DataSnapshot snapshot1:snapshot.getChildren()){
                                       int degree= snapshot1.getValue(Integer.class);
                                       ref.child(Constants.course_path)
                                               .child(id)
                                               .child("members")
                                               .child(Constants.User.getUid())
                                               .child("attendance")
                                               .setValue(degree++);
                                   }
                               }
                               else {
                                   ref.child(Constants.course_path)
                                           .child(id)
                                           .child("members")
                                           .child(Constants.User.getUid())
                                           .child("attendance")
                                           .setValue(1);
                               }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        }
                );
    }

//    private void setAttendace(String id,){
//        int attendance;
//        ref.child(Constants.course_path)
//                .child(id)
//                .child("members")
//                .child(Constants.User.getUid())
//                .addListenerForSingleValueEvent(
//                        new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if(snapshot.hasChild("attendance")){
//                                    for(DataSnapshot snapshot1:snapshot.getChildren()){
//
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        }
//                );
//
//
//
//        ref.child(Constants.course_path)
//                .child(id)
//                .child("members")
//                .child(Constants.User.getUid())
//                .child("attendance").setValue()
//    }
}
