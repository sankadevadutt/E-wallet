package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class mvbooking extends AppCompatActivity {

    ImageView ivback,ivmv1,ivmv2,ivmv3,ivmv4;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvbooking);

        phone = getIntent().getStringExtra("mobile");

        ivback = findViewById(R.id.ivbk7);
        ivmv1 = findViewById(R.id.ivmv1);
        ivmv2 = findViewById(R.id.ivmv2);
        ivmv3 = findViewById(R.id.ivmv3);
        ivmv4 = findViewById(R.id.ivmv4);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), homepage.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        ivmv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("Mvnum","1");
                Intent intent = new Intent(getApplicationContext(), Movieseats.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        ivmv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("Mvnum","2");
                Intent intent = new Intent(getApplicationContext(), Movieseats.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        ivmv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("Mvnum","3");
                Intent intent = new Intent(getApplicationContext(), Movieseats.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        ivmv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("Mvnum","4");
                Intent intent = new Intent(getApplicationContext(), Movieseats.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
    }
}