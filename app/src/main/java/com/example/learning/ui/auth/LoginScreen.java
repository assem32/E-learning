package com.example.learning.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.databinding.ActivityLoginBinding;
import com.example.learning.model.UserModel;
import com.example.learning.ui.instructor.AllCourses;
import com.example.learning.ui.student.StudentHome;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    ActivityLoginBinding binding;

    static public FirebaseAuth auth =FirebaseAuth.getInstance();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        binding.signUpBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(LoginScreen.this, SignUp.class));
                    }
                }
        );

        binding.loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email= binding.editTextEmail.getText().toString();
                        String pass=binding.editTextPassword.getText().toString();
                        validate(email,pass);
                    }
                }
        );



    }
    public void validate (String email,String pass){
        if(email.isEmpty()){
            Toast.makeText(this,"E-mail is empty",Toast.LENGTH_SHORT).show();
        }
        else if(pass.isEmpty()){
            Toast.makeText(this,"Password is empty",Toast.LENGTH_SHORT).show();
        }
        else if(!email.contains("@")||!email.contains(".com")){
            Toast.makeText(this,"Wrong mail format",Toast.LENGTH_SHORT).show();
        }
        else
            login(email,pass);
    }

    public void login(String email,String pass){

        auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(
                new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Intent intent=getIntent();
                        String role = intent.getStringExtra("role");
                        if(role.equals("Student")) {
                            startActivity(new Intent(LoginScreen.this, StudentHome.class));
                        }
                        else if(role.equals("Doctor")){
                            startActivity(new Intent(LoginScreen.this, AllCourses.class));
                        }                        auth.getUid();
                        ref.child(Constants.user_path).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.hasChild(auth.getUid())) {
                                        for(DataSnapshot snapshot1:snapshot.getChildren()) {
                                                Constants.User = snapshot1.getValue(UserModel.class);
                                            System.out.println(Constants.User);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                }
                        );
                        finish();
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginScreen.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }


}
