package com.example.learning.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.databinding.ActivityDocOrStudentBinding;

public class StudentOrProf extends AppCompatActivity {
    ActivityDocOrStudentBinding binding;

    String  studentOrProf;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDocOrStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        binding.studentCheckBox.setChecked(true);


        binding.studentCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked==true) {
                            binding.doctorCheckBox.setChecked(false);
                            studentOrProf="Student";
                        }
                    }
                }
        );

        binding.doctorCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked==true) {
                            binding.studentCheckBox.setChecked(false);
                            studentOrProf="Doctor";
                        }
                    }
                }
        );

        binding.btnNext.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(studentOrProf==null){
                            Toast.makeText(StudentOrProf.this,"Please choose",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent intent =new Intent(StudentOrProf.this, LoginScreen.class);
                            intent.putExtra("role",studentOrProf);
                        startActivity(intent);
                        }
                    }
                }
        );






//        if(binding.studentCheckBox.isChecked())
//        {
//            binding.studentCheckBox.setChecked(true);
//
//            binding.doctorCheckBox.setChecked(false);
//
//        }
//        if (binding.doctorCheckBox.isChecked()){
//            binding.doctorCheckBox.setChecked(true);
//            binding.studentCheckBox.setChecked(false);
//        }

    }
}
