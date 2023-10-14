package com.example.learning.ui.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.Constants;
import com.example.learning.adpter.RecyclerViewMessage;
import com.example.learning.databinding.ActivityChatBinding;
import com.example.learning.model.MessageModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    ActivityChatBinding binding;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    RecyclerViewMessage recyclerViewMessage =new RecyclerViewMessage();

    ArrayList<MessageModel>list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        getData("-NfX3x-HLl_unzQ0Vr8p");

        Intent intent =getIntent();
        String id = intent.getStringExtra("courseId");
        binding.btnSend.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String message=binding.etMessage.getText().toString();
                        sendData(Constants.User.getName(),Constants.User.getUid(),message,String.valueOf(System.currentTimeMillis()),id);
                    }
                }
        );
    }


    private void sendData(String name,String userId,String message,String date,String courseId){
        ref.child(Constants.course_path)
                .child(courseId)
                .child(Constants.message_path)
                .push()
                .setValue(
                        new MessageModel(name,userId,message,date)
                );
    }
    void getData(String courseId){
        ref.child(Constants.course_path).child(courseId).child(Constants.message_path).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()){
                            list.add(snapshot1.getValue(MessageModel.class));

                        }
                        recyclerViewMessage.setList(list);
                        binding.recyclerChat.setAdapter(recyclerViewMessage);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );

    }
}
