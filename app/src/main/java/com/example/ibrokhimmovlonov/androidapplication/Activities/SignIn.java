package com.example.ibrokhimmovlonov.androidapplication.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ibrokhimmovlonov.androidapplication.Admin.AdminSide;
import com.example.ibrokhimmovlonov.androidapplication.Common.Common;
import com.example.ibrokhimmovlonov.androidapplication.Model.User;
import com.example.ibrokhimmovlonov.androidapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {

    MaterialEditText editPhone, editPassword;

    FirebaseDatabase database;

    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Button btnSignIn = findViewById(R.id.btnSignIn);

        editPhone = (MaterialEditText) findViewById(R.id.editPhone);
        editPassword = (MaterialEditText) findViewById(R.id.editPassword);

        // Init Firebase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editPhone.getText().toString().isEmpty() && !editPassword.getText().toString().isEmpty()) {

                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Please waiting...");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            Log.d("TAG", "on tableuser");

                            // Check if user not exist in database
                            if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                                //Get User information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(editPhone.getText().toString()).getValue(User.class);
//                                user.setPhone(editPhone.getText().toString());


                                if (user.getPassword().equals(editPassword.getText().toString())) {
                                    Log.d("MYTAG", user.getType());
                                    if (user.getType().equals("1")) {
                                        //Log.d("MYTAG", "WELCOME TO ADMIN PAGE");
                                    Toast.makeText(SignIn.this, "Welcome to admin page", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignIn.this, AdminSide.class);
                                    Common.currentUser = user;
                                    startActivity(intent);
                                    finish();

                                    } else {
                                        Intent intent = new Intent(SignIn.this, HomePage.class);
                                        Common.currentUser = user;
                                        startActivity(intent);
                                      //  Log.d("MYTAG", "WELCOME TO USER PAGE");
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(SignIn.this, "Wrong password !!!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(SignIn.this, "All fields must be required", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
