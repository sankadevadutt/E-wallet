package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class pswdchange extends AppCompatActivity {

    EditText etNewpswd,etpswdcnfrmsn;
    Button btnChange,btnCancel;
    String phone,pswd;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pswdchange);

        etNewpswd = findViewById(R.id.etNewpswd);
        etpswdcnfrmsn = findViewById(R.id.etpswdcnfrmsn);
        btnCancel = findViewById(R.id.btnCancel);
        btnChange = findViewById(R.id.btnChange);


        phone = getIntent().getStringExtra("mobile");

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etNewpswd.getText().toString().trim().isEmpty()||etpswdcnfrmsn.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }else if(etNewpswd.getText().toString().trim().length() < 6){
                    Toast.makeText(getApplicationContext(),"Password should be min of length 6",Toast.LENGTH_SHORT).show();
                }else if(!etNewpswd.getText().toString().trim().equals(etpswdcnfrmsn.getText().toString().trim()))
                {
                    Toast.makeText(getApplicationContext(),"Password not matched",Toast.LENGTH_SHORT).show();
                }else{
                    pswd = etNewpswd.getText().toString().trim();
                    //storing in database
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");

                    reference.child(phone).child("pswd").setValue(pswd);
                    Toast.makeText(getApplicationContext(),"Password has been changed successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),loginpage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),loginpage.class);
                startActivity(intent);
                finish();
            }
        });
    }


}