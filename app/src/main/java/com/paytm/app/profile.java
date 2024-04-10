package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    TextView tvviewname,tvviewnumb,tvviewmail;
    Button btnback;
    String phone,name,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Linking Variables
        tvviewname = findViewById(R.id.tvviewname);
        tvviewnumb = findViewById(R.id.tvviewnumb);
        tvviewmail = findViewById(R.id.tvviewmail);
        btnback = findViewById(R.id.btnback6);


        phone = getIntent().getStringExtra("mobile");

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(),homepage.class);
                back.putExtra("mobile",phone);
                startActivity(back);
                finish();
            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mail = snapshot.child(phone).child("email").getValue(String.class);
                name = snapshot.child(phone).child("name").getValue(String.class);
                tvviewnumb.setText(phone);
                tvviewmail.setText(mail);
                tvviewname.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}