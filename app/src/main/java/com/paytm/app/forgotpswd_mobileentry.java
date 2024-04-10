package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class forgotpswd_mobileentry extends AppCompatActivity {

    EditText etphonenumb2;
    Button btnext;
    ImageView ivback6;
    String Verificationcode,phone;
    Boolean msgtransfer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpswd_mobileentry);

        etphonenumb2 = findViewById(R.id.etphonenumb2);
        btnext = findViewById(R.id.btnnext);
        ivback6 = findViewById(R.id.ivback6);

        ivback6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),loginpage.class);
                startActivity(intent);
                finish();
            }
        });

        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern p = Pattern.compile("[6-9][0-9]{9}");
                Matcher m = p.matcher(etphonenumb2.getText().toString());
                if(etphonenumb2.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Required field",Toast.LENGTH_SHORT).show();
                }else if(etphonenumb2.getText().toString().length()!=10) {
                    Toast.makeText(getApplicationContext(),"Invalid type!!\n Phone number should be of length 10",Toast.LENGTH_SHORT).show();
                }else if(!m.find()){
                    Toast.makeText(getApplicationContext(),"Invalid type!!\n Phone number should be of the form [6,7,8,9]*********",Toast.LENGTH_SHORT).show();
                }else{

                    phone = etphonenumb2.getText().toString().trim();
                    Accountexist(etphonenumb2);
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
                if(!snapshot.exists())
                {
                    Toast.makeText(getApplicationContext(),"Account doesn't exist",Toast.LENGTH_SHORT).show();
                }else{
                    Verificationcode = otp_generator();
                    sendSMS();
                    if(msgtransfer)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("mobile",etphonenum.getText().toString());
                        bundle.putString("verificationcode",Verificationcode);

                        Intent intent = new Intent(getApplicationContext(),otpforgotpswd.class);
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
            String message = "Your OTP for password change in paytm wallet is:\t"+Verificationcode;
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