package com.example.ibrokhimmovlonov.androidapplication.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.ibrokhimmovlonov.androidapplication.Model.Request;
import com.example.ibrokhimmovlonov.androidapplication.R;
import com.example.ibrokhimmovlonov.androidapplication.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminOrderHistory extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_history);

        // Initialization of Firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        uploadOrderList();
    }

    private void uploadOrderList() {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                reference
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderAddress.setText(model.getAddress());
            }
        };
        recyclerView.setAdapter(adapter);

    }
    private String convertCodeToStatus(String status) {
        Log.d("CHECK", "on convertCode ");

        if (status.equals("0"))
            return "PUT";
        else if (status.equals("1"))
            return "On my way";
        else
            return "DELIVERED";

    }
}
