package com.example.ibrokhimmovlonov.androidapplication.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ibrokhimmovlonov.androidapplication.Database.Database;
import com.example.ibrokhimmovlonov.androidapplication.Interface.ItemClickListener;
import com.example.ibrokhimmovlonov.androidapplication.Model.Food;
import com.example.ibrokhimmovlonov.androidapplication.R;
import com.example.ibrokhimmovlonov.androidapplication.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodlist, favoriteList;

    String categoryId="";

    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;

    Database localDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);


        //Init Firebase
        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Foods");
        favoriteList = database.getReference("Favorites");


        //Local DB
        localDB = new Database(this);


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
            protected void populateViewHolder(final FoodViewHolder viewHolder, final Food model, final int position) {
                Log.d("CHECk", "OnFOODlist on populate");

                viewHolder.food_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.food_image);

                // Add Favorites
                if (localDB.isFavorite(adapter.getRef(position).getKey())) {
                    viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                }

                // Click to change state of Favorites
                viewHolder.fav_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!localDB.isFavorite(adapter.getRef(position).getKey())) {

                            localDB.addInfoToFavorites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_black_24dp);
                            Toast.makeText(FoodList.this, ""+model.getName()+" was added to Favorites", Toast.LENGTH_SHORT).show();

                            //favoriteList.child(Common.currentUser.getName()).setValue(adapter.getRef(position));

                        }
                        else {
                            localDB.removeInfoFromFavorites(adapter.getRef(position).getKey());
                            viewHolder.fav_image.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                            Toast.makeText(FoodList.this, ""+model.getName()+" was removed from Favorites", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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