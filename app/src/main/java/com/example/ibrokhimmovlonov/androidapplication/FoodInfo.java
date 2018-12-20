package com.example.ibrokhimmovlonov.androidapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ibrokhimmovlonov.androidapplication.Database.Database;
import com.example.ibrokhimmovlonov.androidapplication.Model.Food;
import com.example.ibrokhimmovlonov.androidapplication.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodInfo extends AppCompatActivity {

    TextView food_name, food_price, food_info;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton floatingActionButton;
    ElegantNumberButton elegantNumberButton;

    String food_id ="";
    FirebaseDatabase database;
    DatabaseReference food;

    Food current_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        // Init Firebase
        database = FirebaseDatabase.getInstance();
        food = database.getReference("Foods");

        // Init view
        elegantNumberButton = findViewById(R.id.elegant_number);
        floatingActionButton = findViewById(R.id.btn_cart);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addInfoToCart(new Order(
                        food_id,
                        current_food.getName(),
                        elegantNumberButton.getNumber(),
                        current_food.getPrice(),
                        current_food.getDiscount()
                ));
                Toast.makeText(FoodInfo.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        food_info = findViewById(R.id.food_description);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);
        food_image = findViewById(R.id.img_food);

        collapsingToolbarLayout = findViewById(R.id.collapsing_header);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);

        if (getIntent() != null) {
            food_id = getIntent().getStringExtra("FoodId");
        }
        if(!food_id.isEmpty()) {
            getDetailFood(food_id);
        }

    }

    private void getDetailFood(String food_id) {
        food.child(food_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                current_food = dataSnapshot.getValue(Food.class);

                // Set iMAGE
                Picasso.with(getBaseContext()).load(current_food.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(current_food.getName());

                food_price.setText(current_food.getPrice());

                food_name.setText(current_food.getName());

                food_info.setText(current_food.getDescription());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
