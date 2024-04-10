package com.paytm.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class otpforgotpswd extends AppCompatActivity {
    private EditText etcode11,etcode12,etcode13,etcode14,etcode15,etcode16;
    Button btnVerify;
    TextView tvResend,tvMobile;
    String Verificationcode,phone,verifycode;
    Boolean msgtransfer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //To remove Action Bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        //Action bar removed

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpforgotpswd);

        etcode11 = findViewById(R.id.etcode11);
        etcode12 = findViewById(R.id.etcode12);
        etcode13 = findViewById(R.id.etcode13);
        etcode14 = findViewById(R.id.etcode14);
        etcode15 = findViewById(R.id.etcode15);
        etcode16 = findViewById(R.id.etcode16);
        tvMobile = findViewById(R.id.tvMobile);
        btnVerify = findViewById(R.id.btnVerify);
        tvResend = findViewById(R.id.tvResend);

        Bundle bundle = getIntent().getExtras();
        //getting mobile number from signup form
        tvMobile.setText(String.format(
                "+91-%s",bundle.getString("mobile")
        ));
        phone = bundle.getString("mobile");
        verifycode = bundle.getString("verificationcode");

        setupOTPInputs();

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etcode11.getText().toString().trim().isEmpty()
                        || etcode12.getText().toString().trim().isEmpty()
                        || etcode13.getText().toString().trim().isEmpty()
                        || etcode14.getText().toString().trim().isEmpty()
                        || etcode15.getText().toString().trim().isEmpty()
                        || etcode16.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter valid otp code", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code =
                        etcode11.getText().toString() +
                                etcode12.getText().toString() +
                                etcode13.getText().toString() +
                                etcode14.getText().toString() +
                                etcode15.getText().toString() +
                                etcode16.getText().toString();

                if(verifycode.equals(code))
                {

                    Intent intent = new Intent(getApplicationContext(), pswdchange.class);
                    intent.putExtra("mobile",phone);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid OTP!!!\nEnter valid otp",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifycode = otp_generator();
                sendSMS();
            }
        });

    }

    public void setupOTPInputs(){
        etcode11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode12.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode13.requestFocus();
                }else{
                    etcode11.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode14.requestFocus();
                }else{
                    etcode12.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode15.requestFocus();
                }else{
                    etcode13.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etcode15.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty())
                {
                    etcode16.requestFocus();
                }else{
                    etcode14.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etcode16.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty())
                {
                    etcode15.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
            String message = "Your OTP for sign up in paytm wallet is:\t"+verifycode;
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