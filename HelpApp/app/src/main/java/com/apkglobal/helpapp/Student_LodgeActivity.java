package com.apkglobal.helpapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Student_LodgeActivity extends AppCompatActivity {
    private EditText etname, etplace, etmobile, etmessage;
    private Button lodgeButton,selectimg;
    private DatabaseReference refer;

    private FirebaseUser firebaseUser;
    private FirebaseAuth auth1;
    private String uid, name, place, mobile, messege,cget,date;
    private  String r;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private ProgressDialog dialog;
    private StorageReference storageReference;
   // private ProgressDialog progressDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__lodge);

        etname = (EditText) findViewById(R.id.Stuname);
        etplace = (EditText) findViewById(R.id.place);
        etmobile = (EditText) findViewById(R.id.Mobile);
        etmessage = (EditText) findViewById(R.id.EditTextFeedbackBody);
        selectimg = (Button)findViewById(R.id.button5);
        lodgeButton = (Button) findViewById(R.id.lbutton);
        dialog = new ProgressDialog(this);




        refer = FirebaseDatabase.getInstance().getReference().child("Complaints");
        //for reference to storage of image
        storageReference= FirebaseStorage.getInstance().getReference().child("Complaint_images");
        selectimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();

            }
        });




        lodgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Complaint is Uploading...");
                dialog.show();
                Date mydate= new Date();
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-mm-dd hh-mm-ss a",Locale.getDefault());
                date=simpleDateFormat.format(mydate);
                name = etname.getText().toString();
                place = etplace.getText().toString();
                mobile = etmobile.getText().toString();
                messege = etmessage.getText().toString();
                Random random= new Random();
                Random random1= new Random();
                int r1 = 100+ random.nextInt(50000000);
                int r2 = 1+ random1.nextInt(5000);
                int r3=r1 * r2;
                r=String.valueOf(r3);
                uploadimage();


                //CreateComplaint createComplaint = new CreateComplaint(name, place, mobile, messege,date,"image");


                /*refer.child(r).setValue(createComplaint).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //save the image to firebase storage
                            final StorageReference sr = storageReference.child(name + r + ".jpg");
                            sr.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                                    String s= sr.getDownloadUrl().toString();
                                    sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String download_image_path = task.getResult().toString();
                                            refer.child(r).child("imageurl").setValue(download_image_path)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                dialog.dismiss();
                                                                Toast.makeText(getApplicationContext(),"Complaint Uploaded",Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });

                                        }
                                    });
                                }
                            });
                        }
                    }
                });*/




            }
        });




    }

    private void chooseimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();

        }
    }

    private void uploadimage()
    {
        if(filePath!=null)
        {
            //save the image to firebase storage
            final StorageReference sr = storageReference.child(name + r + ".jpg");
            sr.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                   sr.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           Toast.makeText(getApplicationContext(),"Complaint uploaded",Toast.LENGTH_LONG).show();
                           CreateComplaint createComplaint = new CreateComplaint(name, place, mobile, messege,date,uri.toString());
                           refer.child(r).setValue(createComplaint);
                           dialog.dismiss();

                       }
                   });

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
           /* sr.putFile(filePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        final String downloadpath =task.getResult().getMetadata().getReference().getDownloadUrl().toString();
                        Toast.makeText(getApplicationContext(),"Complaint uploaded",Toast.LENGTH_LONG).show();
                        CreateComplaint createComplaint = new CreateComplaint(name, place, mobile, messege,date,downloadpath);
                        refer.child(r).setValue(createComplaint);
                        dialog.dismiss();

                    }
                }
            });*/

        }
        else{
            Toast.makeText(getApplicationContext(),"No File Selected",Toast.LENGTH_LONG).show();
        }
    }

}
