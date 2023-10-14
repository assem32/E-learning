package com.example.learning.ui.student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.learning.adpter.RecyclerViewMaterial;
import com.example.learning.databinding.ActivityStudentMaterialBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MaterialPage extends AppCompatActivity

{
    ActivityStudentMaterialBinding binding;

    ArrayList<String> list = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    RecyclerViewMaterial recyclerViewMaterial =new RecyclerViewMaterial();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        binding=ActivityStudentMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);
        getData("-NfLBWsQiThfsaU9xOnE");
    }


    private void getData(String id){
        ref.child("CourseFile").child(id).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snapshot1 :snapshot.getChildren()){
                            list.add(snapshot1.getValue(String.class));
                        }
                        recyclerViewMaterial.setList(list);
                        binding.recyclerStudentMaterial.setAdapter(recyclerViewMaterial);
                        recyclerViewMaterial.setOnClickMaterial(
                                new RecyclerViewMaterial.OnClickMaterial() {
                                    @Override
                                    public void onItemClick(int index) {
                                        String Url=list.get(index);
                                        Intent intent=new Intent(Intent.ACTION_VIEW);
                                        intent.setData(Uri.parse(Url));
                                        startActivity(intent);
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
