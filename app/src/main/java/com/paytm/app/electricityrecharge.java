package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class electricityrecharge extends AppCompatActivity {

    ImageView ivback;
    Button btnpay;
    EditText etservicenum,etrech;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricityrecharge);

        phone = getIntent().getStringExtra("mobile");

        ivback = findViewById(R.id.ivbk6);
        btnpay = findViewById(R.id.btnpay2);
        etservicenum = findViewById(R.id.etservicenum);
        etrech = findViewById(R.id.etrech);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), recharge.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etservicenum.getText().toString().isEmpty())
                {
                    etservicenum.setError("Required field");
                }else{
                    Pattern p = Pattern.compile("^[1-9]\\d{15}$");
                    Matcher m = p.matcher(etservicenum.getText().toString());
                    if(m.find())
                    {
                        if(etrech.getText().toString().isEmpty())
                        {
                            etrech.setError("Required field");
                        }else{
                            Bundle b = new Bundle();
                            b.putString("mobile",phone);
                            b.putString("sernum",etservicenum.getText().toString());
                            b.putString("recieved","electricityrecharge");
                            b.putString("total",etrech.getText().toString());
                            Intent intent = new Intent(getApplicationContext(), payment.class);
                            intent.putExtras(b);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        etservicenum.setError("Invalid service number");
                    }

                }
            }
        });
    }
}