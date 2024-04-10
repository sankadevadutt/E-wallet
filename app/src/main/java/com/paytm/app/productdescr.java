package com.paytm.app;

import androidx.annotation.NonNull;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class productdescr extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    Button btnadd;
    ImageView ivbk4,ivprdct;
    TextView tvpdtn,tvprice,tvdes1,tvdes2,tvdes3,tvdes4,tvdes5;
    Spinner spinnercolor;
    ArrayList<String>color = new ArrayList<>(Arrays.asList("Select color"));
    String phone,category,type,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdescr);

        btnadd = findViewById(R.id.btnadd);
        ivbk4 = findViewById(R.id.ivbk4);
        ivprdct = findViewById(R.id.ivprdct);
        tvpdtn = findViewById(R.id.tvpdtn);
        tvprice = findViewById(R.id.tvprice);
        tvdes1 = findViewById(R.id.tvdes1);
        tvdes2 = findViewById(R.id.tvdes2);
        tvdes3 = findViewById(R.id.tvdes3);
        tvdes4 = findViewById(R.id.tvdes4);
        tvdes5 = findViewById(R.id.tvdes5);
        spinnercolor = findViewById(R.id.spinnercolor);

        Bundle b = getIntent().getExtras();
        phone = b.getString("mobile");
        category = b.getString("category");
        type = b.getString("type");
        image = b.getString("image");


        switch (category)
        {
            case "Mobiles":
                switch (image)
                {
                    case"1":
                        ivprdct.setImageResource(R.drawable.iphone12);
                        break;
                    case"2":
                        ivprdct.setImageResource(R.drawable.mi11x);
                        break;
                    case"3":
                        ivprdct.setImageResource(R.drawable.op9);
                        break;
                    case"4":
                        ivprdct.setImageResource(R.drawable.pixel);
                        break;
                }
                break;
            case "Accessories":
                switch (image)
                {
                    case"1":
                        ivprdct.setImageResource(R.drawable.ac1);
                        break;
                    case"2":
                        ivprdct.setImageResource(R.drawable.ac2);
                        break;
                    case"3":
                        ivprdct.setImageResource(R.drawable.ac3);
                        break;
                    case"4":
                        ivprdct.setImageResource(R.drawable.ac4);
                        break;
                }
                break;
        }


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Shopping").child("categories").child(category).child(type);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvprice.setText("Price : "+snapshot.child("Price").getValue(String.class));
                tvpdtn.setText(snapshot.child("pdtn").getValue(String.class));
                tvdes1.setText(snapshot.child("Description").child("des1").getValue(String.class));
                tvdes2.setText(snapshot.child("Description").child("des2").getValue(String.class));
                tvdes3.setText(snapshot.child("Description").child("des3").getValue(String.class));
                tvdes4.setText(snapshot.child("Description").child("des4").getValue(String.class));
                tvdes5.setText(snapshot.child("Description").child("des5").getValue(String.class));
                for(DataSnapshot snapshot1 : snapshot.child("colors").getChildren())
                {
                        color.add(String.valueOf(snapshot1.getValue(String.class)));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnercolor.getSelectedItem().toString().equals("Select color"))
                {
                    Toast.makeText(productdescr.this, "Select color", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("mobile",phone);
                    bundle.putString("category",category);
                    bundle.putString("type",type);
                    bundle.putString("prdctname",tvpdtn.getText().toString());
                    bundle.putString("image",image);
                    bundle.putString("recieved","productdescr");
                    String total = tvprice.getText().toString().replaceAll(",","");
                    bundle.putString("total",total.substring(10));
                    Intent intent = new Intent(getApplicationContext(),payment.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
















        spinnercolor.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.color_spinner_layout,color);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnercolor.setAdapter(adapter);




        ivbk4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("mobile",phone);
                b.putString("category",category);
                Intent intent = new Intent(getApplicationContext(), phonespng.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
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