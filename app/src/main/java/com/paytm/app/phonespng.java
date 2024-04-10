package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class phonespng extends AppCompatActivity {

    ImageView ivbk2,ivitem1,ivitem2,ivitem3,ivitem4;
    TextView tvitem1,tvitem2,tvitem3,tvitem4;
    String phone,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonespng);

        ivbk2 = findViewById(R.id.ivbk2);
        ivitem1 = findViewById(R.id.ivitem1);
        ivitem2 = findViewById(R.id.ivitem2);
        ivitem3 = findViewById(R.id.ivitem3);
        ivitem4 = findViewById(R.id.ivitem4);
        tvitem1 = findViewById(R.id.tvitem1);
        tvitem2 = findViewById(R.id.tvitem2);
        tvitem3 = findViewById(R.id.tvitem3);
        tvitem4 = findViewById(R.id.tvitem4);

        Bundle b = getIntent().getExtras();

        phone = b.getString("mobile");
        category = b.getString("category");

        if(category.equals("Mobiles"))
        {
            ivitem1.setImageResource(R.drawable.iphone12);
            tvitem1.setText("Iphone 12(128gb)");
            ivitem2.setImageResource(R.drawable.mi11x);
            tvitem2.setText("Mi11X Pro(8,128)");
            ivitem3.setImageResource(R.drawable.op9);
            tvitem3.setText("Oneplus 9pro 5G");
            ivitem4.setImageResource(R.drawable.pixel);
            tvitem4.setText("Google pixel 4a");
        }else{
            ivitem1.setImageResource(R.drawable.ac1);
            tvitem1.setText("Apple watch 6");
            ivitem2.setImageResource(R.drawable.ac2);
            tvitem2.setText("OnePlus Buds");
            ivitem3.setImageResource(R.drawable.ac3);
            tvitem3.setText("Google Nest Mini");
            ivitem4.setImageResource(R.drawable.ac4);
            tvitem4.setText("Mi headphones");
        }

        ivbk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), spngtypes.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });


        ivitem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("category",category);
                b.putString("type",tvitem1.getText().toString());
                b.putString("image","1");
                Intent intent = new Intent(getApplicationContext(), productdescr.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
        ivitem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("category",category);
                b.putString("type",tvitem2.getText().toString());
                b.putString("image","2");
                Intent intent = new Intent(getApplicationContext(), productdescr.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
        ivitem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("category",category);
                b.putString("type",tvitem3.getText().toString());
                b.putString("image","3");
                Intent intent = new Intent(getApplicationContext(), productdescr.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });
        ivitem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("category",category);
                b.putString("type",tvitem4.getText().toString());
                b.putString("image","4");
                Intent intent = new Intent(getApplicationContext(), productdescr.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

    }
}