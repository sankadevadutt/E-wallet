package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Movieseats extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    String phone,Mvnum;

    ImageView ivback,ivmv;
    Spinner spinnerseats;
    Button btnpay;
    String mvname;
    int Total=0;

    ArrayList<Integer>seats = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10));
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movieseats);

        ivback = findViewById(R.id.ivbk8);
        ivmv = findViewById(R.id.ivmv);
        spinnerseats = findViewById(R.id.spinnerseats);
        btnpay = findViewById(R.id.btnpay3);

        Bundle b = getIntent().getExtras();
        phone = b.getString("mobile");
        Mvnum = b.getString("Mvnum");
        switch (Mvnum)
        {
            case "1":
                ivmv.setImageResource(R.drawable.mv1);
                mvname = "Godzilla vs Kong";
                break;
            case "2":
                ivmv.setImageResource(R.drawable.mv2);
                mvname = "Tom and jerry";
                break;
            case "3":
                ivmv.setImageResource(R.drawable.mv3);
                mvname = "The Father";
                break;
            case "4":
                ivmv.setImageResource(R.drawable.mv4);
                mvname = "Mortal Kombat";
                break;
        }

        spinnerseats.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.color_spinner_layout,seats);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerseats.setAdapter(adapter);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mvbooking.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnerseats.getSelectedItem().toString().equals("0"))
                {
                    Toast.makeText(Movieseats.this, "Select valid number of seats", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle b = new Bundle();
                    b.putString("mobile",phone);
                    b.putString("recieved","Movieseats");
                    b.putString("Mvnum",Mvnum);
                    b.putString("seatscou",spinnerseats.getSelectedItem().toString());
                    b.putString("Mvname",mvname);
                    b.putString("total",String.valueOf(Integer.parseInt(spinnerseats.getSelectedItem().toString())*200));
                    Intent intent = new Intent(getApplicationContext(), payment.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }



            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}