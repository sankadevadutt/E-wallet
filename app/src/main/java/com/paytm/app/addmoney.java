package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class addmoney extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener  {

    String phone;
    EditText etbaladd,etpaycvv2;
    Spinner spinnercards2;
    Button btnget2;
    ImageView ivback;
    ArrayList<String> cards = new ArrayList<>();
    int cardbal,bal,wallet;
    String date,card,cvv;
    int month,year,curmon = 5,curyear = 21;
    Boolean ismoneyadd = false;


    FirebaseDatabase rootNode;
    DatabaseReference reference1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmoney);

        phone = getIntent().getExtras().getString("mobile");
        wallet = getIntent().getExtras().getInt("wallet");

        etbaladd = findViewById(R.id.etbaladd);
        spinnercards2 = findViewById(R.id.spinnercards2);
        btnget2 = findViewById(R.id.btnget2);
        ivback = findViewById(R.id.ivback);
        etpaycvv2 = findViewById(R.id.etpaycvv2);

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

        spinnercards2.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.color_spinner_layout,cards);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnercards2.setAdapter(adapter);

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),wallet.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });


        btnget2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etbaladd.getText().toString().isEmpty())
                {
                   etbaladd.setError("Required field");
                }else{
                    bal = Integer.parseInt(etbaladd.getText().toString());
                    if(spinnercards2.getSelectedItem().toString().equals("Choose"))
                    {
                        ismoneyadd = false;
                        Toast.makeText(getApplicationContext(), "Select card number", Toast.LENGTH_SHORT).show();
                    }if(etpaycvv2.getText().toString().isEmpty())
                    {
                        ismoneyadd = false;
                        etpaycvv2.setError("Required field");
                    }else{
                        card = spinnercards2.getSelectedItem().toString();
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("users").child(phone).child("Cards");
                        Query check = reference1.orderByChild("cardnum").equalTo(card);
                        check.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                cardbal = Integer.parseInt(snapshot.child(card).child("balance").getValue(String.class));
                                cvv = snapshot.child(card).child("cvv").getValue(String.class);
                                date = snapshot.child(card).child("expdate").getValue(String.class);
                                String[] split = date.split("/");
                                month = Integer.parseInt(split[0]);
                                year = Integer.parseInt(split[1]);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        if(!etpaycvv2.getText().toString().equals(cvv))
                        {
                            ismoneyadd = false;
                            Toast.makeText(addmoney.this, "Invalid cvv", Toast.LENGTH_SHORT).show();
                        }else{
                            if(cardbal<bal)
                            {
                                ismoneyadd = false;
                                Toast.makeText(getApplicationContext(), "Insufficient balance in the card", Toast.LENGTH_SHORT).show();
                            }else if(year<curyear)
                            {
                                ismoneyadd = false;
                                Toast.makeText(getApplicationContext(), "Card is expired cant be used", Toast.LENGTH_SHORT).show();
                            }else if(year==curyear)
                            {
                                if(month<curmon)
                                {
                                    ismoneyadd = false;
                                    Toast.makeText(getApplicationContext(), "Card is expired cant be used", Toast.LENGTH_SHORT).show();
                                }else {
                                    ismoneyadd = true;
                                }
                            }else{
                                ismoneyadd = true;
                            }

                            if(ismoneyadd)
                            {
                                Toast.makeText(addmoney.this, "Balance added to wallet", Toast.LENGTH_SHORT).show();
                                cardbal= cardbal-bal;
                                rootNode = FirebaseDatabase.getInstance();
                                reference1 = rootNode.getReference().child("users").child(phone);
                                reference1.child("wallet_bal").setValue(wallet+bal);
                                reference1.child("Cards").child(card).child("balance").setValue(""+cardbal);
                                Intent intent = new Intent(getApplicationContext(),wallet.class);
                                intent.putExtra("mobile",phone);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }

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