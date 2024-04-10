package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {
    ImageView ivback;
    EditText etname,etphonenum,etemail,etpswd,etconfirmpswd;
    Button btnconfirm;
    Boolean isnameValid = false,ispswdValid = false,isphoneValid = false,ismailValid = false,iscnfrmValid = false,msgtransfer = false;
    String name,pswd,mail,phone;
    String Verificationcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);



        //Linking variables
        ivback = findViewById(R.id.ivback2);
        etname = findViewById(R.id.etnameedit);
        etphonenum = findViewById(R.id.etphoneedit);
        etconfirmpswd = findViewById(R.id.etconfirmpswd);
        etemail = findViewById(R.id.etemailedit);
        etpswd = findViewById(R.id.etpswd);
        btnconfirm = findViewById(R.id.btnconfirm);


        ActivityCompat.requestPermissions(signup.this,new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);



        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(getApplicationContext(),loginpage.class);
                startActivity(back);
                finish();
            }
        });


        etpswd.setError("Required field");
        etphonenum.setError("Required field");
        etname.setError("Required field");
        etconfirmpswd.setError("Required field");



        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validate_phone(etphonenum);
                Validate_name(etname);
                Validate_mail(etemail);
                Validate_pass(etpswd);

                if(etconfirmpswd.getText().toString().isEmpty())
                {
                    iscnfrmValid = false;
                    etconfirmpswd.setError("Required field");
                }else if(!etpswd.getText().toString().equals(etconfirmpswd.getText().toString()))
                {
                    iscnfrmValid = false;
                    etconfirmpswd.setError("Passwords doesn't match");
                }else{
                    pswd = etpswd.getText().toString().trim();
                    iscnfrmValid = true;
                }


                if(iscnfrmValid && ismailValid && isnameValid && isnameValid && isphoneValid && ispswdValid )
                {
                    Accountexist(etphonenum);
                }
            }
        });
    }

    private void Accountexist(EditText etphonenum) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuser = reference.orderByChild("phone").equalTo(phone);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Toast.makeText(getApplicationContext(),"Account already created",Toast.LENGTH_SHORT).show();
                }else{
                    Verificationcode = otp_generator();
                    sendSMS();
                    if(msgtransfer)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile",etphonenum.getText().toString());
                        bundle.putString("verificationcode",Verificationcode);
                        bundle.putString("mail",mail);
                        bundle.putString("name",name);
                        bundle.putString("pswd",pswd);

                        Intent intent = new Intent(getApplicationContext(),otpsignup.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void Validate_pass(EditText data) {
        if(data.getText().toString().trim().isEmpty())
        {
            ispswdValid = false;
            data.setError("Required field");
        }else if(data.getText().toString().trim().length()<6)
        {
            ispswdValid = false;
            data.setError("Password should be minimum of length 6");
        }else if(data.getText().toString().trim().length()>15){
            ispswdValid = false;
            data.setError("Maximum Password length is 15");
        }else{
            ispswdValid = true;
        }
    }




    private void Validate_mail(EditText data) {
        Pattern p = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty())
        {
            ismailValid = true;
            mail = "Nill";
        }else if(data.getText().toString().length()>=320) {
            ismailValid = false;
            data.setError("Invalid type!!! email should not be greater than 320 leters");
        }else if(!m.find()){
            ismailValid = false;
            data.setError("Invalid email type");
        }else{
            ismailValid = true;
            mail = data.getText().toString().trim();
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
            name_modifier = data.getText().toString().trim().substring(0,32);
            name = "";
            String [] changer = name_modifier.split("\\s");
            for(String s: changer)
            {
                String first = s.substring(0,1).toUpperCase();
                name += first+s.substring(1)+" ";
            }
            name = name.trim();
        }else if(!m.find()){
            isnameValid = false;
            data.setError("Invalid type name should contain only letters and spaces");
        }else{
            isnameValid = true;
            name_modifier = data.getText().toString().trim();
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
            name = name.trim();
        }


    }


    private void Validate_phone(EditText data) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(data.getText().toString());
        if(data.getText().toString().trim().isEmpty())
        {
            isphoneValid = false;
            data.setError("Required field");
        }else if(data.getText().toString().length()!=10) {
            isphoneValid = false;
            data.setError("Invalid type!!! Phone number should be of length 10");
        }else if(!m.find()){
            isphoneValid = false;
            data.setError("Invalid type Phone number should be of the form [6,7,8,9]*********");
        }else{
            isphoneValid = true;
            phone = data.getText().toString().trim();
        }
    }

    public String otp_generator()
    {
        Random rndm_method = new Random();

        int num=0;
        String numbers = "0123456789";
        String otp;
        char[] otp1 = new char[6];

        for (int i = 0; i < 6; i++)
        {
            otp1[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
        }
        otp = String.valueOf(otp1);
        return otp;
    }

    private void sendSMS()
    {
        try{
            String message = "Your OTP for sign up in paytm wallet is:\t"+Verificationcode;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null);
            msgtransfer = true;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"OTP failed to deliver",Toast.LENGTH_SHORT).show();
            msgtransfer = false;
        }
    }
}