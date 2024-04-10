package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class homepage extends AppCompatActivity {
    ImageView ivpv,ivpe,ivcart,ivwallet,ivmt,ivbp;
    TextView tvpv,tvpe,tvcart,tvwallet,tvmt,tvbp;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //Linking variables
        ivpv = findViewById(R.id.ivpv);
        ivpe = findViewById(R.id.ivpe);
        ivcart = findViewById(R.id.ivcart);
        ivwallet = findViewById(R.id.ivwallet);
        tvpv = findViewById(R.id.tvpv);
        tvpe = findViewById(R.id.tvpe);
        tvcart = findViewById(R.id.tvcart);
        tvwallet = findViewById(R.id.tvwallet);
        ivbp = findViewById(R.id.ivbp);
        ivmt = findViewById(R.id.ivmt);
        tvbp = findViewById(R.id.tvbp);
        ivbp = findViewById(R.id.ivbp);



        phone = getIntent().getStringExtra("mobile");



        //profileview
        ivpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(), profile.class);
                profile.putExtra("mobile",phone);
                startActivity(profile);
            }
        });

        tvpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(getApplicationContext(), profile.class);
                profile.putExtra("mobile",phone);
                startActivity(profile);
            }
        });

        //profile edit
        ivpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileedit = new Intent(getApplicationContext(), profile_edit.class);
                profileedit.putExtra("mobile",phone);
                startActivity(profileedit);
            }
        });

        tvpe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileedit = new Intent(getApplicationContext(), profile_edit.class);
                profileedit.putExtra("mobile",phone);
                startActivity(profileedit);
            }
        });


        //shopping
        ivcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(), spngtypes.class);
                cart.putExtra("mobile",phone);
                startActivity(cart);
            }
        });

        tvcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(getApplicationContext(), spngtypes.class);
                cart.putExtra("mobile",phone);
                startActivity(cart);
            }
        });


        //wallet
        ivwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wallet = new Intent(getApplicationContext(), wallet.class);
                wallet.putExtra("mobile",phone);
                startActivity(wallet);
            }
        });

        tvwallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wallet = new Intent(getApplicationContext(), wallet.class);
                wallet.putExtra("mobile",phone);
                startActivity(wallet);
            }
        });

        //billpay
        ivbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wallet = new Intent(getApplicationContext(), recharge.class);
                wallet.putExtra("mobile",phone);
                startActivity(wallet);
            }
        });


        //movie
        ivmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wallet = new Intent(getApplicationContext(), mvbooking.class);
                wallet.putExtra("mobile",phone);
                startActivity(wallet);
            }
        });


    }
}