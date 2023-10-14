package com.example.learning.ui.instructor;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.databinding.ActivityCourseDetailsBinding;

public class CourseDetails extends AppCompatActivity {
    ActivityCourseDetailsBinding binding;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding= ActivityCourseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String id=intent.getStringExtra("courseId");


        binding.openToUploadNewMatrialInCourseHomeDocotr.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CourseDetails.this,PDFViewer.class);

                        Intent intent1=getIntent();
                        String id =intent1.getStringExtra("courseId");

                        intent.putExtra("courseId1",id);

                        Log.d("courseId1",id);
                        startActivity(intent);
                    }
                }
        );

       binding.btnOpenTakeAttendance.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent1=getIntent();
                       String id =intent1.getStringExtra("courseId");
                       Intent intent = new Intent(CourseDetails.this,GenerateCode.class);
                       intent.putExtra("courseId",id);
                       startActivity(intent);
                   }
               }
       );

       binding.showAllGradeInCourseHomeDocotr.setOnClickListener(
               new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent1=getIntent();
                       String id =intent1.getStringExtra("courseId");
                       Intent intent = new Intent(CourseDetails.this, Grade.class);
                       intent.putExtra("courseId",id);
                       startActivity(intent);
                   }
               }
       );

        binding.openQuzeToMakeItInCourseHomeDocotr.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1=getIntent();
                        String id =intent1.getStringExtra("courseId");
                        Intent intent = new Intent(CourseDetails.this,CreateQuiz.class);
                        intent.putExtra("courseId",id);
                        startActivity(intent);
                    }
                }

        );

        binding.btnAddMember.setText(id);
        binding.btnAddMember.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("label", id);
                        clipboard.setPrimaryClip(clip);
                    }
                }
        );


    }

}
