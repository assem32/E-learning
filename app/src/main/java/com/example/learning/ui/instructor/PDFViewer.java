package com.example.learning.ui.instructor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.learning.Constants;
import com.example.learning.R;
import com.example.learning.databinding.ActivityPdfBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PDFViewer extends AppCompatActivity {


    ActivityPdfBinding binding;
    Uri resulPDF;

    FirebaseAuth auth =FirebaseAuth.getInstance();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    StorageReference storage = FirebaseStorage.getInstance().getReference();
    ActivityResultLauncher<String> nTakePdf;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding=ActivityPdfBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String id = intent.getStringExtra("courseId1");

        nTakePdf=registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        resulPDF=result;
                        binding.pdfView.fromUri(resulPDF).load();
                    }
                }
        );

        binding.btnAddPdf.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nTakePdf.launch("application/pdf");
                    }
                }
        );
        binding.btnUpload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pdfToStorage(id);
                    }
                }
        );

    }



    private void pdfToStorage(String id){
        String name = "pdf/"+auth.getUid()+System.currentTimeMillis();
        storage.child(name).putFile(resulPDF).addOnSuccessListener(
                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storage.child(name).getDownloadUrl().addOnSuccessListener(
                                new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        uploadPdf(id,uri.toString());
                                    }
                                }
                        );
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                }
        );
    }

    private void uploadPdf(String id,String url){

        ref.child("CourseFile").child(id).push().setValue(url).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PDFViewer.this,"done",Toast.LENGTH_SHORT);
                    }
                }
        ).addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PDFViewer.this,e.toString(),Toast.LENGTH_SHORT);
                    }
                }
        );
    }
}
