package com.rajat.firebaseuserloginusingphoto;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rajat.firebaseuserloginusingphoto.databinding.ActivityMainBinding;

import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
   ActivityMainBinding binding;
   Uri imageURI;
   ActivityResultLauncher<Intent> imageSelectionResultLauncher = registerForActivityResult(
           new ActivityResultContracts.StartActivityForResult(),
           new ActivityResultCallback<ActivityResult>() {
               @Override
               public void onActivityResult(ActivityResult result) {
                   if(result.getResultCode() == Activity.RESULT_OK){
                       //
                       imageURI = result.getData().getData();
                       binding.profileImage.setImageURI(imageURI);
                   }

               }
           }
   );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnImage();
            }
        });
        //
        binding.buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.progressText.setVisibility(View.VISIBLE);
                uploadUserData();


            }
        });
    }

    private void uploadUserData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now=new Date();
        String imageID="images/"+sdf.format(now);
        StorageReference  storage=FirebaseStorage.getInstance().getReference(imageID);
        storage.putFile(imageURI).addOnSuccessListener(
                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //                               String firstName,       String lastName,                                 String jobrole,                              String pimage,        String username                      , String userpassword
                        UserModel um = new UserModel(binding.firstName.getText().toString(),binding.lastName.getText().toString(),binding.jobRole.getSelectedItem().toString(),imageID,binding.userName.getText().toString(),binding.password.getText().toString());
                        FirebaseDatabase db=FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference("users");
                        root.child(binding.userID.getText().toString()).setValue(um);
                        binding.userID.setText("");
                        binding.firstName.setText("");
                        binding.lastName.setText("");
                        binding.userName.setText("");
                        binding.password.setText("");
                        binding.profileImage.setImageResource(R.drawable.ic_launcher_background);
                        binding.progressBar.setProgress(0);
                        binding.progressText.setText("0.0%");
                        binding.progressBar.setVisibility(View.GONE);
                        binding.progressText.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"User Registration Succcss",Toast.LENGTH_LONG).show();

                    }

                }
        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FIREBASE",e.getMessage());
                binding.progressBar.setProgress(0);
                binding.progressText.setText("0.0%");
                binding.progressBar.setVisibility(View.GONE);
                binding.progressText.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"User Registration Failed!!!!",Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                binding.progressBar.setProgress((int)progress);
                binding.progressText.setText(progress+"%");
            }
        });
    }

    private void selectAnImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        imageSelectionResultLauncher.launch(intent);
    }
}