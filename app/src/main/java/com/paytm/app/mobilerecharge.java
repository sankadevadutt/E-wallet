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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class mobilerecharge extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    EditText etbalphone;
    ImageView ivback;
    Spinner spinnersim,spinnerbal;
    Button btnpay;
    String phone;

    ArrayList<String>sims = new ArrayList<String>(Arrays.asList("Select sim type", "Jio","Airtel","BSNL","VI"));
    ArrayList<String>bala = new ArrayList<String>(Arrays.asList("Select Amount","19","49","99","199","399","599","999"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilerecharge);

        phone = getIntent().getStringExtra("mobile");

        etbalphone = findViewById(R.id.etbalphone);
        ivback = findViewById(R.id.ivbk5);
        spinnerbal = findViewById(R.id.spinnerbal);
        spinnersim = findViewById(R.id.spinnersim);
        btnpay = findViewById(R.id.btnpay);

        spinnersim.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.color_spinner_layout,sims);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnersim.setAdapter(adapter);

        spinnerbal.setOnItemSelectedListener(this);
        ArrayAdapter adapter1 = new ArrayAdapter(this, R.layout.color_spinner_layout,bala);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnerbal.setAdapter(adapter1);

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

                if(Validate_num(etbalphone))
                {
                    if(spinnersim.getSelectedItem().toString().equals("Select sim type"))
                    {
                        Toast.makeText(mobilerecharge.this, "Select Sim Type", Toast.LENGTH_SHORT).show();
                    }else if(spinnerbal.getSelectedItem().toString().equals("Select Amount"))
                    {
                        Toast.makeText(mobilerecharge.this,"Select Balance amount" , Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Bundle b = new Bundle();
                        b.putString("mobile",phone);
                        b.putString("recharmob",etbalphone.getText().toString());
                        b.putString("recieved","mobilerecharge");
                        b.putString("total",spinnerbal.getSelectedItem().toString());
                        Intent intent = new Intent(getApplicationContext(), payment.class);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        });



    }
    private boolean Validate_num(EditText data) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty())
        {
            data.setError("Required field");
            return  false;
        }else if(data.getText().toString().length()!=10) {
            data.setError("Invalid type!!! Phone number should be of length 10");
            return false;
        }else if(!m.find()){
            data.setError("Invalid type Phone number should be of the form [6,7,8,9]*********");
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}