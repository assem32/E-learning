package com.example.learning.ui.instructor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.R;
import com.example.learning.databinding.ActivityAttendanceGenerateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class GenerateCode extends AppCompatActivity {
    ActivityAttendanceGenerateBinding binding;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    int randomNumber =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityAttendanceGenerateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        String id =intent.getStringExtra("courseId");

        binding.btnGenerateCode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Random random = new Random();

                        // Generate a random number between 1000 and 9999 (inclusive)
                         randomNumber = random.nextInt(9000) + 1000;

                        System.out.println("Random Number: " + randomNumber);
                        binding.textCode.setText(String.valueOf(randomNumber));
                        binding.btnConfirm.setEnabled(true);
                        binding.btnConfirm.setBackgroundResource(R.drawable.button_background_blue);
                        binding.btnConfirm.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(GenerateCode.this,"Lecture Created",Toast.LENGTH_SHORT).show();

                                        ref.child("Attendance")
                                                .child(id)
                                                .child(String.valueOf(randomNumber))
                                                .setValue("").addOnSuccessListener(
                                                        new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                Toast.makeText(GenerateCode.this,"Lecture Created",Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                ).addOnFailureListener(
                                                        new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(GenerateCode.this,e.toString(),Toast.LENGTH_SHORT).show();

                                                            }
                                                        }
                                                );
                                    }
                                }
                        );
                    }
                }
        );


            binding.btnConfirm.setBackgroundColor(0xff808080);





    }
}
