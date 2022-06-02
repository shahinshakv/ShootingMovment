package com.shahinsha.shootmovements;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.shahinsha.shootmovements.model.Login;
import com.shahinsha.shootmovements.model.fcm.DataModel;
import com.shahinsha.shootmovements.model.fcm.NotificationModel;
import com.shahinsha.shootmovements.model.fcm.RootModel;
import com.shahinsha.shootmovements.retrofit.ApiClient;
import com.shahinsha.shootmovements.retrofit.ApiInterface;
import com.shahinsha.shootmovements.utility.TinyDB;
import com.squareup.okhttp.ResponseBody;

import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity {
    TinyDB tinyDB;
    boolean isLogin = false;
    TextView nametxt,emailtxt;
    ImageButton btnlogout;
    ConstraintLayout sendfcm;
    ImageButton recordbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nametxt = findViewById(R.id.nametxt);
        emailtxt = findViewById(R.id.emailtxt);
        btnlogout = findViewById(R.id.btnlogout);
        sendfcm = findViewById(R.id.sendfcm);
        recordbtn = findViewById(R.id.recordbtn);

        tinyDB = new TinyDB(this);
        isLogin  = tinyDB.getBoolean("isLogin");
        recordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,30);
                startActivityForResult(intent,1);
            }
        });
        if (isLogin){
            Login data = tinyDB.getObject("User", Login.class);
            nametxt.setText(data.getData().getName());
            emailtxt.setText(data.getData().getEmail());
        }

        sendfcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = tinyDB.getString("token");
                sendNotificationToUser(token, "Welcome "+ nametxt.getText().toString());
            }
        });

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

    private void sendNotificationToUser(String token, String name) {
        RootModel rootModel = new RootModel(token, new NotificationModel("Shooting Movements", name), new DataModel("Name", "30"));

        ApiInterface apiService =  ApiClient.getClient().create(ApiInterface.class);
        retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendNotification(rootModel);
        Toast.makeText(getApplicationContext(),"sending....",Toast.LENGTH_SHORT).show();

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("TAG","Successfully notification send by using retrofit.");
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            VideoView videoView = new VideoView(this);
            videoView.setVideoURI(data.getData());
            videoView.start();
            builder.setView(videoView).show();
        }
    }
}