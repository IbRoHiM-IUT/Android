package com.example.ibrokhimmovlonov.androidapplication.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ibrokhimmovlonov.androidapplication.Activities.OrderList;
import com.example.ibrokhimmovlonov.androidapplication.Common.Common;
import com.example.ibrokhimmovlonov.androidapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminSide extends AppCompatActivity {

    Button add_food, orderList;
    TextView phoneNumber, userName, user_email;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_side);

        userName = findViewById(R.id.user_name);
        phoneNumber = findViewById(R.id.user_phone_number);
        user_email = findViewById(R.id.user_email);
        add_food = findViewById(R.id.add_food);
        orderList = findViewById(R.id.order_button);

        // Initialization of Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        userName.setText(Common.currentUser.getName());
        phoneNumber.setText(Common.currentUser.getPhone());
        user_email.setText(Common.currentUser.getName()+"@gmail.com");

        add_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSide.this, AdminAddFood.class);
                startActivity(intent);
            }
        });

        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminSide.this, AdminOrderHistory.class);
                startActivity(intent);
            }
        });

    }
}
