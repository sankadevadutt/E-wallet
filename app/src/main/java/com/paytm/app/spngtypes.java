package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class spngtypes extends AppCompatActivity {

    ImageView ivbk,ivpn,ivac;
    TextView tvpn,tvac;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spngtypes);

        phone = getIntent().getStringExtra("mobile");

        ivbk = findViewById(R.id.ivbk);
        ivpn = findViewById(R.id.ivpn);
        ivac = findViewById(R.id.ivac);
        tvpn = findViewById(R.id.tvpn);
        tvac = findViewById(R.id.tvac);

        ivbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), homepage.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });



        ivpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("category","Mobiles");
                Intent intent = new Intent(getApplicationContext(), phonespng.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        ivac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("category","Accessories");
                Intent intent = new Intent(getApplicationContext(), phonespng.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });


    }
}