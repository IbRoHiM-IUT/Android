package com.example.ibrokhimmovlonov.androidapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ibrokhimmovlonov.androidapplication.Interface.ItemClickListener;
import com.example.ibrokhimmovlonov.androidapplication.Model.Food;
import com.example.ibrokhimmovlonov.androidapplication.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodlist;

    String categoryId="";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Init Firebase
        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Foods");

        recyclerView = findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get Intent here

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
            Log.d("CHECk", "OnFOODlist ");
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListFood(categoryId);
            Log.d("CHECk", "OnFOODlist on second if  ");

        }


    }

    private void loadListFood(String categoryId) {
        Log.d("CHECk", "OnFOODlist on loadListFood ");

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.food_item, FoodViewHolder.class,
                foodlist) // like SELECT * FROM Foods WHERE MenuId =
//        foodlist.orderByChild("MenuId").equalTo(categoryId)
        {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                Log.d("CHECk", "OnFOODlist on populate");

                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent food_info = new Intent(FoodList.this, FoodInfo.class);
                        food_info.putExtra("FoodId", adapter.getRef(position).getKey());    // Send FoodId to new activity
                        startActivity(food_info);

                    }
                });
            }
        };
        // Set adapter
        recyclerView.setAdapter(adapter);
    }
}