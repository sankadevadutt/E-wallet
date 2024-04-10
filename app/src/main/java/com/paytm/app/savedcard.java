package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import java.util.Map;

public class savedcard extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener  {

    String phone;
    ImageView ivback;
    TextView tvcardnum,tved,tvbal,tvcn;
    Button btnget;
    Spinner spinnercards;
    CardView cvcard;

    ArrayList<String> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savedcard);

        ivback = findViewById(R.id.ivback4);
        btnget = findViewById(R.id.btnget);
        tvcardnum = findViewById(R.id.tvcardnum);
        tved = findViewById(R.id.tved);
        tvbal = findViewById(R.id.tvbal);
        tvcn = findViewById(R.id.tvcn);
        spinnercards = findViewById(R.id.spinnercards);
        cvcard = findViewById(R.id.cvcard);
        cvcard.setVisibility(View.GONE);

        phone = getIntent().getStringExtra("mobile");

        cards.add("Choose");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(phone).child("Cards");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    Map<String,Object> map = (Map<String, Object>)snapshot1.getValue();
                    Object cardn = map.get("cardnum");
                    cards.add(String.valueOf(cardn));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinnercards.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.color_spinner_layout,cards);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnercards.setAdapter(adapter);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),wallet.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spinnercards.getSelectedItem().toString().equals("Choose"))
                {
                    cvcard.setVisibility(View.GONE);
                    Toast.makeText(savedcard.this, "Select card number", Toast.LENGTH_SHORT).show();
                }else{
                    cvcard.setVisibility(View.VISIBLE);
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("users").child(phone).child("Cards");
                    Query check = reference1.orderByChild("cardnum").equalTo(spinnercards.getSelectedItem().toString());
                    check.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            tvbal.setText(snapshot.child(spinnercards.getSelectedItem().toString()).child("balance").getValue(String.class));
                            tvcn.setText(snapshot.child(spinnercards.getSelectedItem().toString()).child("name").getValue(String.class));
                            tvcardnum.setText(spinnercards.getSelectedItem().toString());
                            tved.setText(snapshot.child(spinnercards.getSelectedItem().toString()).child("expdate").getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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