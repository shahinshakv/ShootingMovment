package com.shahinsha.shootmovements;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.shahinsha.shootmovements.model.Login;
import com.shahinsha.shootmovements.retrofit.ApiRequest;
import com.shahinsha.shootmovements.retrofit.RetrofitRequest;
import com.shahinsha.shootmovements.utility.TinyDB;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInPage extends AppCompatActivity {


    private ApiRequest apiRequest;
    EditText edtemail,edtpass;
    Button btnlogin;
    TextView signuptxt;
    TinyDB tinyDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edtemail = findViewById(R.id.edtemail);
        edtpass = findViewById(R.id.edtpass);
        btnlogin = findViewById(R.id.btnlogin);
        signuptxt = findViewById(R.id.signuptxt);
        tinyDB =new TinyDB(SignInPage.this);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtemail.getText().length()>0 && edtpass.getText().length()>0) {
                    GLoginApi(edtemail.getText().toString(), edtpass.getText().toString());
                }else {
                    Toast.makeText(getApplicationContext(), "Enter the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInPage.this, SignUp.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void GLoginApi(String email, String password) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getApplicationContext().getColor(R.color.colorprimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        try {
            Map<String, String> params = new HashMap<>();

            params.put("email", email);
            params.put("password", password);




            apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
            apiRequest.loginemail(params)
                    .enqueue(new Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> call, Response<Login> response) {
                            pDialog.dismiss();
                            Log.i("Signin", "onResponse: " + response.body());
                            if (response.body() != null) {
                                if (response.body().getCode()==0) {
                                    Log.i("Signin", "onResponse: " + response.body());
                                    tinyDB.putBoolean("isLogin", true);
                                    tinyDB.putObject("User", response.body());
                                    Log.i("Signin" ,"onResponse: ");
                                    Toast.makeText(getApplicationContext(), response.body().getMessage()+" "+response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SignInPage.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {

                                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                Log.i("SignIn", "onResponse: what is the error " + response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            pDialog.dismiss();
                            Log.i("SignIn", "onFailure: " + t);

                            Toast.makeText(getApplicationContext(), t.toString() + "fail", Toast.LENGTH_SHORT).show();

                        }
                    });
        } catch (Exception e) {
            pDialog.dismiss();
            e.printStackTrace();
        }
    }

}