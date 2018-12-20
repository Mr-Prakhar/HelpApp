package com.apkglobal.helpapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;
    DatabaseReference mRef;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        dialog = new ProgressDialog(this);

        dialog.setMessage("Loading...");
        dialog.show();

        if(user == null)
        {
            dialog.dismiss();
            setContentView(R.layout.activity_main);
           // manager = new PermissionManager() {};
           // manager.checkAndRequestPermissions(this);

        }
        else
        {

            uid=user.getUid();

            Toast.makeText(getApplicationContext(), "Successfully Logged in", Toast.LENGTH_SHORT).show();
            mRef=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child("utype").exists()){

                        String utyps=dataSnapshot.child("utype").getValue().toString();
                        Log.i("itype",utyps);


                        //for Jump to Student Activity
                        if (utyps.equals("Student"))
                        {
                            dialog.dismiss();
                            Intent intent=new Intent(getApplicationContext(),StudentActivity.class);
                            startActivity(intent);
                        }
                        // For Jump to  Proctorial Board Activity
                        else if (utyps.equals("Proctorial Board"))
                        {
                            dialog.dismiss();
                            Intent intent=new Intent(getApplicationContext(),ProctorialActivity.class);
                            startActivity(intent);
                        }
                        else if(utyps.equals("Security"))
                        {
                            dialog.dismiss();
                            Intent intent=new Intent(getApplicationContext(),SecurityActivity.class);
                            startActivity(intent);
                        }
                        else if(utyps.equals("Faculty"))
                        {
                            dialog.dismiss();
                            Intent intent=new Intent(getApplicationContext(),FacultyActivity.class);
                            startActivity(intent);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
    public void goToLogin(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(myIntent);
    }


    // for goto Register page using intent
    public void goToRegister(View v)
    {
        Intent myIntent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(myIntent);
    }
}
