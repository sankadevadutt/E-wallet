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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profile_edit extends AppCompatActivity {

    EditText etnameedit,etemailedit;
    TextView tvviewnumb2;
    ImageView ivback2;
    Button btnalter;
    String phone,name,mail,new_name,new_mail;
    Boolean ismailValid=false,isnameValid=false;

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
        setContentView(R.layout.activity_profile_edit);

        etemailedit = findViewById(R.id.etemailedit);
        etnameedit = findViewById(R.id.etnameedit);
        ivback2 = findViewById(R.id.ivback2);
        btnalter = findViewById(R.id.btnconfirm);
        tvviewnumb2 = findViewById(R.id.tvviewnumb2);

        phone = getIntent().getStringExtra("mobile");

        ivback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(),homepage.class);
                back.putExtra("mobile",phone);
                startActivity(back);
                finish();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mail = snapshot.child(phone).child("email").getValue(String.class);
                name = snapshot.child(phone).child("name").getValue(String.class);
                etnameedit.setText(name);
                etemailedit.setText(mail);
                tvviewnumb2.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnalter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etnameedit.getText().toString().equals(name) && etemailedit.getText().toString().equals(mail) )
                {
                    Toast.makeText(getApplicationContext(),"Nothing has been modified",Toast.LENGTH_SHORT).show();
                }else {
                    Validate_mail(etemailedit);
                    Validate_name(etnameedit);
                    if(isnameValid && ismailValid)
                    {
                        rootNode = FirebaseDatabase.getInstance();
                        reference1 = rootNode.getReference("users");

                        reference1.child(phone).child("email").setValue(new_mail);
                        reference1.child(phone).child("name").setValue(new_name);
                        Toast.makeText(getApplicationContext(),"Data has been changed successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),profile.class);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });
    }

    private void Validate_mail(EditText data) {
        Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty()||data.getText().toString().trim().equals("Nill"))
        {
            ismailValid = true;
            new_mail = "Nill";
        }else if(data.getText().toString().length()>=320) {
            ismailValid = false;
            data.setError("Invalid type!!! email should not be greater than 320 leters");
        }else if(!m.find()){
            ismailValid = false;
            data.setError("Invalid email type");
        }else{
            ismailValid = true;
            new_mail = data.getText().toString().trim();
        }
    }



    private void Validate_name(EditText data) {
        Pattern p = Pattern.compile("^[a-z\\s]+$",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(data.getText().toString());
        String name_modifier="";
        if(data.getText().toString().trim().isEmpty())
        {
            isnameValid = false;
            data.setError("Required field");
        }else if(data.getText().toString().length()>=32) {
            isnameValid = true;
            /*takes only initial 32 characters as an input*/
            name_modifier = data.getText().toString().trim().substring(0,32);

            /*making capital letters after space
             *like name second
             * changes to
             * Name Second
             */
            name = "";
            String [] changer = name_modifier.split("\\s");
            for(String s: changer)
            {
                String first = s.substring(0,1).toUpperCase();
                name += first+s.substring(1)+" ";
            }
            new_name = name.trim();
        }else if(!m.find()){
            isnameValid = false;
            data.setError("Invalid type name should contain only letters and spaces");
        }else{
            isnameValid = true;
            name_modifier = data.getText().toString().trim();
            name = "";
            String [] changer = name_modifier.split("\\s");
            for(String s: changer)
            {
                String first = s.substring(0,1).toUpperCase();
                name += first+s.substring(1)+" ";
            }
            new_name = name.trim();
        }

    }


}