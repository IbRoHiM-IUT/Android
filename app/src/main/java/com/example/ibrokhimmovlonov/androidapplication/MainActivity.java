package com.example.ibrokhimmovlonov.androidapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Nabila.ttf");
        TextView textView = findViewById(R.id.title);
        textView.setTypeface(face);

        Button btnSignUp = findViewById(R.id.btnSignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Signup = new Intent(MainActivity.this, SignUp.class);
                startActivity(Signup);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Signin = new Intent(MainActivity.this, SignIn.class);
                startActivity(Signin);
            }
        });
    }
}
