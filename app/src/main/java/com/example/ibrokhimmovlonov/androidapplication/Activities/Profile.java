package com.example.ibrokhimmovlonov.androidapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ibrokhimmovlonov.androidapplication.Common.Common;
import com.example.ibrokhimmovlonov.androidapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    TextView phoneNumber, userName, user_email;
    Button btn_order;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.user_name);
        phoneNumber = findViewById(R.id.user_phone_number);
        user_email = findViewById(R.id.user_email);
        btn_order = findViewById(R.id.order_button);

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(Profile.this,OrderList.class);
                startActivity(orderIntent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        userName.setText(Common.currentUser.getName());
        phoneNumber.setText(Common.currentUser.getPhone());
        user_email.setText(Common.currentUser.getName()+"@gmail.com");



    }



}
