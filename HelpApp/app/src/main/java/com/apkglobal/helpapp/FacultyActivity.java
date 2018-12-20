package com.apkglobal.helpapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FacultyActivity extends AppCompatActivity {
    ImageView logout, deleteacc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        logout = (ImageView) findViewById(R.id.imageView);
        deleteacc = (ImageView) findViewById(R.id.imageView5);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });

        deleteacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }

    public void goToLodgeActivity(View v) {
        Intent myIntent = new Intent(getApplicationContext(), Student_LodgeActivity.class);
        startActivity(myIntent);
    }

    public void goToShowActivity(View v) {
        Intent myIntent = new Intent(getApplicationContext(), Proctorial_ShowActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }


    //for delete account
    private void deleteAccount() {
        //Log.d(TAG, "ingreso a deleteAccount");
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Log.d(TAG,"OK! Works fine!");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Toast.makeText(getApplicationContext(), "Your Account has been Deleted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Log.e(TAG,"Ocurr an error during account delete", e);
                Toast.makeText(getApplicationContext(), "Your account could not be deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
