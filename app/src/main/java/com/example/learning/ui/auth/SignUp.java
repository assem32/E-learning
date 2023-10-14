package com.example.learning.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.MainActivity;
import com.example.learning.databinding.ActivityRegisterBinding;
import com.example.learning.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth auth =FirebaseAuth.getInstance();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email=binding.editTextEmail.getText().toString();
                        String pass=binding.editTextPassword.getText().toString();
                        String name = binding.editTextName.getText().toString();
                        validate(email,name,pass);
                    }
                }
        );

        super.onCreate(savedInstanceState);
    }


    public void validate (String email,String name,String pass){
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
            register(name,email,pass);
    }
    private void register(String name,String email,String pass){
        auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                putTheDataRealTime(name,email, auth.getUid());

            }
        });

    }

    private void putTheDataRealTime(String name,String mail,String uid){
        ref.child( Constants.user_path).child(uid).setValue(new UserModel(name,mail,uid)).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity( new Intent(SignUp.this, MainActivity.class));
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this,"error",Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

}
