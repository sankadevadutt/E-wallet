package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class recharge extends AppCompatActivity {

    ImageView ivpn2,ivel,ivback;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        ivpn2 = findViewById(R.id.ivpn2);
        ivel = findViewById(R.id.ivel);
        ivback = findViewById(R.id.ivbk3);

        phone = getIntent().getStringExtra("mobile");

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), homepage.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        ivel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), electricityrecharge.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        ivpn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mobilerecharge.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });


    }
}