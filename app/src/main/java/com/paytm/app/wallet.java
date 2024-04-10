package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class wallet extends AppCompatActivity {
    ImageView ivback;
    TextView tvbalance;
    Button btnsc,btnac,btnam;
    String phone;

    int bal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        ivback = findViewById(R.id.ivback3);
        tvbalance = findViewById(R.id.tvbalance);
        btnsc = findViewById(R.id.btnsc);
        btnac = findViewById(R.id.btnac);
        btnam = findViewById(R.id.btnam);

        phone = getIntent().getStringExtra("mobile");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query check = reference.orderByChild("phone").equalTo(phone);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bal = snapshot.child(phone).child("wallet_bal").getValue(int.class);
                tvbalance.setText(String.valueOf(bal)+" ₹");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),homepage.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });


        btnsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sc = new Intent(getApplicationContext(),savedcard.class);
                sc.putExtra("mobile",phone);
                startActivity(sc);
                finish();
            }
        });
        btnac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ac = new Intent(getApplicationContext(),addcard.class);
                ac.putExtra("mobile",phone);
                startActivity(ac);
                finish();
            }
        });
        btnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent am = new Intent(getApplicationContext(),addmoney.class);
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putInt("wallet",bal);
                am.putExtras(b);
                startActivity(am);
                finish();
            }
        });
    }
}