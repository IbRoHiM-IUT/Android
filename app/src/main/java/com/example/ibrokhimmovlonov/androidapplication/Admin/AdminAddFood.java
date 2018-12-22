package com.example.ibrokhimmovlonov.androidapplication.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ibrokhimmovlonov.androidapplication.Model.Food;
import com.example.ibrokhimmovlonov.androidapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdminAddFood extends AppCompatActivity {


    @BindView(R.id.food_name_admin)
    EditText name;
    @BindView(R.id.food_price_admin)
    EditText price;
    @BindView(R.id.food_description_admin)
    EditText desc;
    @BindView(R.id.upload_image_admin)
    Button upload;
    @BindView(R.id.submit_admin)
    Button submit;
    Uri uri;
    Food food;
    private final int IMAGE_REEQUEST = 71;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseStorage storage;
    StorageReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_food);
        ButterKnife.bind(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Foods");

        storage = FirebaseStorage.getInstance();
        reference = storage.getReference();


    }


    @OnClick(R.id.upload_image_admin)
    public void upload() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_REEQUEST);

    }

    @OnClick(R.id.submit_admin)
    public void submitting() {
        if (uri != null) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading to server...");
            dialog.show();

            String imageName = UUID.randomUUID().toString();
            final StorageReference folder = reference.child("image/" + imageName);
            folder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    dialog.dismiss();
                    Toast.makeText(AdminAddFood.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    folder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            food = new Food();
                            food.setName(name.getText().toString());
                            food.setDescription(desc.getText().toString());
                            food.setPrice(price.getText().toString());
                            food.setImage(uri.toString());
                            food.setDiscount("0");
                            food.setMenuId("01");
                            pushToFirebase(food);
                            dialog.dismiss();
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminAddFood.this, "error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void pushToFirebase(final Food food) {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference.child(food.getName()).setValue(food);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REEQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            upload.setText("Image selected");

        }
    }

}
