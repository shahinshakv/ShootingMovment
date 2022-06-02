package com.shahinsha.shootmovements;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shahinsha.shootmovements.model.Login;
import com.shahinsha.shootmovements.utility.TinyDB;

public class HomeActivity extends AppCompatActivity {
    TinyDB tinyDB;
    boolean isLogin = false;
    TextView nametxt,emailtxt;
    Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nametxt = findViewById(R.id.nametxt);
        emailtxt = findViewById(R.id.emailtxt);
        btnlogout = findViewById(R.id.btnlogout);

        tinyDB = new TinyDB(this);
        isLogin  = tinyDB.getBoolean("isLogin");
        if (isLogin){
            Login data = tinyDB.getObject("User", Login.class);
            nametxt.setText(data.getData().getName()+"");
            emailtxt.setText(data.getData().getEmail()+"");
        }

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinyDB.putBoolean("isLogin",false);
                Intent i = new Intent(HomeActivity.this, SignInPage.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(HomeActivity.this, SignInPage.class);
        startActivity(i);
        finish();
    }
}