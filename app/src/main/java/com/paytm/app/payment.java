package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class payment extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    ImageView ivbk;
    TextView tvtotal,tvwallet2;
    EditText etwalletded,etpaycvv;
    Spinner spinnercards3;
    Button btnproceed;
    String date,card;
    int month,year,curmon = 5,curyear = 21;
    Boolean ispaid=false;
    int x,y,z,t;
    String phone,recieved,cvv;
    int amount,wallet;
    String total = "Total amount to be paid is ₹ ";
    String message = "";
    FirebaseDatabase rootNode;
    DatabaseReference reference1;

    ArrayList<String>cards = new ArrayList<>(Arrays.asList("Choose"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Bundle b = getIntent().getExtras();
        phone = b.getString("mobile");
        recieved = b.getString("recieved");
        amount = Integer.parseInt(b.getString("total"));

        ivbk = findViewById(R.id.ivbk9);
        tvtotal = findViewById(R.id.tvtotal);
        etwalletded = findViewById(R.id.etwalletded);
        etpaycvv = findViewById(R.id.etpaycvv);
        spinnercards3 = findViewById(R.id.spinnercards3);
        btnproceed = findViewById(R.id.btnproceed);
        tvwallet2 = findViewById(R.id.tvwallet2);


        tvtotal.setText(total+amount);

        DatabaseReference refe = FirebaseDatabase.getInstance().getReference("users");
        Query check = refe.orderByChild("phone").equalTo(phone);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wallet = snapshot.child(phone).child("wallet_bal").getValue(int.class);
                tvwallet2.setText("Available amount in the wallet is ₹"+wallet);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







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

        spinnercards3.setOnItemSelectedListener(this);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.color_spinner_layout,cards);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinnercards3.setAdapter(adapter);


        btnproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etwalletded.getText().toString().isEmpty())
                {
                    etwalletded.setError("Enter 0 in case of nill");
                }else
                {
                    x = Integer.parseInt(etwalletded.getText().toString());
                    y = wallet;
                    t = amount;
                    if(x>t)
                    {
                        ispaid = false;
                        etwalletded.setError("Exceeded total payable amount\nEnter value <= "+t);
                    }else if(x>y)
                    {
                        ispaid = false;
                        Toast.makeText(payment.this, "Exceeded wallet balance\nEnter value <= "+wallet, Toast.LENGTH_SHORT).show();
                    }else if(x == y && x!=t)
                    {
                        if(spinnercards3.getSelectedItem().toString().equals("Choose"))
                        {
                            ispaid = false;
                            Toast.makeText(payment.this, "Select card", Toast.LENGTH_SHORT).show();
                        }else if(etpaycvv.getText().toString().isEmpty())
                        {
                            ispaid = false;
                            etpaycvv.setError("Required field");
                        }else{
                            card = spinnercards3.getSelectedItem().toString();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("users").child(phone).child("Cards");
                            Query check = reference1.orderByChild("cardnum").equalTo(card);
                            check.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    z = Integer.parseInt(snapshot.child(card).child("balance").getValue(String.class));
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
                            if(!etpaycvv.getText().toString().equals(cvv))
                            {
                                ispaid = false;
                                Toast.makeText(payment.this, "Invalid cvv", Toast.LENGTH_SHORT).show();
                            }else{
                                t = t-x;
                                if(z<t)
                                {
                                    ispaid = false;
                                    Toast.makeText(payment.this, "Insufficient balance in the card", Toast.LENGTH_SHORT).show();
                                }else  if(year<curyear)
                                {
                                    ispaid= false;
                                    Toast.makeText(getApplicationContext(), "Card is expired cant be used", Toast.LENGTH_SHORT).show();
                                }else if(year==curyear)
                                {
                                    if(month<curmon)
                                    {
                                        ispaid = false;
                                        Toast.makeText(getApplicationContext(), "Card is expired cant be used", Toast.LENGTH_SHORT).show();
                                    }else {
                                        ispaid = true;
                                    }
                                }else{

                                    ispaid = true;
                                    y = 0;
                                    z=z-t;
                                    ispaid = true;
                                    rootNode = FirebaseDatabase.getInstance();
                                    reference1 = rootNode.getReference().child("users").child(phone);
                                    reference1.child("wallet_bal").setValue(y);
                                    reference1.child("Cards").child(card).child("balance").setValue(""+z);
                                }
                            }


                        }
                    }else if(x!=y && x!=t)
                    {
                        if(spinnercards3.getSelectedItem().toString().equals("Choose"))
                        {
                            ispaid = false;
                            Toast.makeText(payment.this, "Select card", Toast.LENGTH_SHORT).show();
                        }else if(etpaycvv.getText().toString().isEmpty())
                        {
                            ispaid = false;
                            etpaycvv.setError("Required field");
                        }else{
                            card = spinnercards3.getSelectedItem().toString();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("users").child(phone).child("Cards");
                            Query check = reference1.orderByChild("cardnum").equalTo(card);
                            check.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    z = Integer.parseInt(snapshot.child(card).child("balance").getValue(String.class));
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
                            if(!etpaycvv.getText().toString().equals(cvv))
                            {
                                ispaid = false;
                                Toast.makeText(payment.this, "Invalid cvv", Toast.LENGTH_SHORT).show();
                            }else{
                                t = t-x;
                                if(z<t)
                                {
                                    ispaid = false;
                                    Toast.makeText(payment.this, "Insufficient balance in the card", Toast.LENGTH_SHORT).show();
                                }else  if(year<curyear)
                                {
                                    ispaid= false;
                                    Toast.makeText(getApplicationContext(), "Card is expired cant be used", Toast.LENGTH_SHORT).show();
                                }else if(year==curyear)
                                {
                                    if(month<curmon)
                                    {
                                        ispaid = false;
                                        Toast.makeText(getApplicationContext(), "Card is expired cant be used", Toast.LENGTH_SHORT).show();
                                    }else {
                                        ispaid = true;
                                    }
                                }else {
                                    ispaid = true;
                                    y = y-x;
                                    z=z-t;
                                    ispaid = true;
                                    rootNode = FirebaseDatabase.getInstance();
                                    reference1 = rootNode.getReference().child("users").child(phone);
                                    reference1.child("wallet_bal").setValue(y);
                                    reference1.child("Cards").child(card).child("balance").setValue(""+z);
                                }
                            }


                        }
                    }else if(x==y && x==t)
                    {
                        ispaid = true;
                        Toast.makeText(payment.this, "No need to validate card details", Toast.LENGTH_SHORT).show();
                        y = 0;
                        rootNode = FirebaseDatabase.getInstance();
                        reference1 = rootNode.getReference().child("users").child(phone);
                        reference1.child("wallet_bal").setValue(y);
                    }else if(x!=y && x==t)
                    {
                        ispaid = true;
                        Toast.makeText(payment.this, "No need to validate card details", Toast.LENGTH_SHORT).show();
                        y = y-x;
                        rootNode = FirebaseDatabase.getInstance();
                        reference1 = rootNode.getReference().child("users").child(phone);
                        reference1.child("wallet_bal").setValue(y);
                    }
                }
                if(ispaid)
                {
                    switch (recieved)
                    {
                        case "Movieseats":
                            Bundle bundle1 = getIntent().getExtras();
                            message = bundle1.getString("seatscou")+" seats are booked for movie "+bundle1.getString("Mvname")+" successfully\nEnjoy the day";
                            sendSMS(phone,message);
                            break;
                        case "electricityrecharge":
                            Bundle bundle2 = getIntent().getExtras();
                            message = "Recharge of ₹ "+amount+" for service number "+bundle2.getString("sernum")+" is successful";
                            sendSMS(phone,message);
                            break;
                        case "mobilerecharge":
                            Bundle bundle3 = getIntent().getExtras();
                            message = "Recharge of ₹ "+amount+" for mobile number "+bundle3.getString("recharmob")+" is successful";
                            sendSMS(bundle3.getString("recharmob"),message);
                            break;
                        case "productdescr":
                            Bundle bundle4 = getIntent().getExtras();
                            message = "Payment for purchase of the product "+bundle4.getString("prdctname")+" is successful";
                            sendSMS(phone,message);
                            break;
                    }




                    Toast.makeText(payment.this, "Payment successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), homepage.class);
                    intent.putExtra("mobile",phone);
                    startActivity(intent);
                    finish();
                }

            }
        });









        ivbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(phone,"Payment of ₹ "+amount+" is halted");
                switch (recieved)
                {
                    case "Movieseats":
                        Intent intent = new Intent(getApplicationContext(), Movieseats.class);
                        Bundle bundle1 = getIntent().getExtras();
                        Bundle b1 = new Bundle();
                        b1.putString("mobile",phone);
                        b1.putString("Mvnum",bundle1.getString("Mvnum"));
                        intent.putExtras(b1);
                        startActivity(intent);
                        finish();
                        break;
                    case "electricityrecharge":
                        Intent intent1 = new Intent(getApplicationContext(), electricityrecharge.class);
                        intent1.putExtra("mobile",phone);
                        startActivity(intent1);
                        finish();
                        break;
                    case "mobilerecharge":
                        Intent intent2 = new Intent(getApplicationContext(), mobilerecharge.class);
                        intent2.putExtra("mobile",phone);
                        startActivity(intent2);
                        finish();
                        break;
                    case "productdescr":
                        Intent intent3 = new Intent(getApplicationContext(),productdescr.class);
                        Bundle b = new Bundle();
                        Bundle bundle = getIntent().getExtras();
                        b.putString("mobile",phone);
                        b.putString("category",bundle.getString("category"));
                        b.putString("type",bundle.getString("type"));
                        b.putString("image",bundle.getString("image"));
                        intent3.putExtras(b);
                        startActivity(intent3);
                        finish();
                        break;
                }
            }
        });
    }

    private void sendSMS(String phonecontact,String message)
    {
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phonecontact,null,message,null,null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}