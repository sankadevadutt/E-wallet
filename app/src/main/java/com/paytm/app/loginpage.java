package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class loginpage extends AppCompatActivity {

    EditText etPhoneLgn,etPswdlgn;
    Button btnLogin;
    TextView tvpassforg,tvsignup;
    Boolean isDataValid = false;
    String phone,pswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        //Linking of variables
        etPhoneLgn = findViewById(R.id.etPhoneLgn);
        etPswdlgn = findViewById(R.id.etPswdlgn);
        btnLogin = findViewById(R.id.btnLogin);
        tvpassforg = findViewById(R.id.tvpassforg);
        tvsignup = findViewById(R.id.tvsignup);

        ActivityCompat.requestPermissions(loginpage.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        tvpassforg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginpage.this,forgotpswd_mobileentry.class);
                startActivity(intent);
            }
        });

        tvsignup.setOnClickListener(view -> {
            Intent intent = new Intent(loginpage.this, signup.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //validate phone number
                phone = etPhoneLgn.getText().toString().trim();
                pswd = etPswdlgn.getText().toString().trim();
                //validate and verify phone number with password
                Validate_phone(etPhoneLgn);
                Validate_pswd(etPswdlgn);
                if(isDataValid)
                {
                    isUser();
                }
            }
        });
    }

    private void isUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String pswdfromdb = snapshot.child(phone).child("pswd").getValue(String.class);

                    if(pswdfromdb.equals(pswd)){
                        Intent home = new Intent(loginpage.this,homepage.class);
                        home.putExtra("mobile",phone);
                        startActivity(home);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"Invalid password",Toast.LENGTH_SHORT).show();
                        etPswdlgn.requestFocus();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Account doesn't exist",Toast.LENGTH_LONG).show();
                    etPhoneLgn.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Validate_pswd(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            isDataValid = false;
            data.setError("Required field");
        }

    }

    private void Validate_phone(EditText data) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty())
        {
            isDataValid = false;
            data.setError("Required field");
        }else if(data.getText().toString().length()!=10) {
            isDataValid = false;
            data.setError("Invalid type Phone number should be of length 10");
        }else if(!m.find()){
            isDataValid = false;
            data.setError("Invalid type Phone number should be of the form [6,7,8,9]*********");
        }else{
            isDataValid = true;
        }

    }
}