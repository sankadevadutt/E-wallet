package com.paytm.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class addcard extends AppCompatActivity {

    Button btnsave;
    EditText etcardnum,etexpdate,etcvv,etname,etbalance;
    String cardnum,expdate,cvv,name,balance,phone;
    Boolean isvalidnum=false,isvaliddate=false,isvalidcvv=false,isvalidname=false,isvalidbal=false;
    ImageView ivback;


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
        setContentView(R.layout.activity_addcard);


        //Linking variables
        btnsave = findViewById(R.id.btnsave);
        etcardnum = findViewById(R.id.etcardnum);
        etexpdate = findViewById(R.id.etexpdate);
        etcvv = findViewById(R.id.etcvv);
        etname = findViewById(R.id.etname);
        etbalance = findViewById(R.id.etbalance);
        ivback = findViewById(R.id.ivback5);

        phone = getIntent().getStringExtra("mobile");

        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),wallet.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
                finish();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validatenum(etcardnum);
                validatedate(etexpdate);
                validatecvv(etcvv);
                validatename(etname);
                validatebal(etbalance);
                if(isvalidnum && isvalidbal && isvalidcvv && isvaliddate && isvaliddate)
                {
                    isadd();

                }
            }
        });

    }

    private void isadd() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkuser = reference.child(phone).child("Cards").orderByChild("cardnum").equalTo(cardnum);

        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Toast.makeText(getApplicationContext(),"Card has been already added",Toast.LENGTH_SHORT).show();
                }else {
                    rootNode = FirebaseDatabase.getInstance();
                    reference1 = rootNode.getReference("users");

                    usersecondhelper helperClass = new usersecondhelper(cardnum,expdate,cvv,name,balance);

                    reference1.child(phone).child("Cards").child(cardnum).setValue(helperClass);
                    Toast.makeText(addcard.this, "Contact has been added succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),wallet.class);
                    intent.putExtra("mobile",phone);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Validatenum(EditText etcardnum) {
        cardnum = etcardnum.getText().toString();
        if(cardnum.isEmpty())
        {
            isvalidnum = false;
            etcardnum.setError("Required field");
        }else{
            if(etcardnum.getText().toString().length()>16 || etcardnum.getText().toString().length()<13)
            {
                etcardnum.setError("Invalid CardNum");
            }else{
                long number = Long.parseLong(cardnum);
                isvalidnum = isValid(number)?true:false;
                if(!isvalidnum)
                {
                    etcardnum.setError("Invalid card number");
                }
            }
        }

    }
    private void validatedate(EditText etexpdate) {
        Pattern date = Pattern.compile("^(0?[1-9]|1[0-2])[\\/\\-][1-9]{2}$");
        Matcher m = date.matcher(etexpdate.getText().toString());
        expdate = etexpdate.getText().toString();
        if(expdate.isEmpty())
        {
            isvaliddate = false;
            etexpdate.setError("Required field");
        }else if(m.find()){
            isvaliddate = true;
        }else {
            isvaliddate = false;
            etexpdate.setError("Invalid date");
        }
    }
    private void validatecvv(EditText etcvv) {
        cvv = etcvv.getText().toString();
        Pattern p = Pattern.compile("^[0-9]{3}$");
        Matcher m = p.matcher(cvv);
        if(cvv.isEmpty())
        {
            isvalidcvv = false;
            etcvv.setError("Required field");
        }else{
            isvalidcvv = m.find();
            if(!isvalidcvv)
            {
                etcvv.setError("Invalid cvv");
            }
        }
    }
    private void validatename(EditText data) {
        Pattern p = Pattern.compile("^[a-z\\s]+$",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(data.getText().toString());
        String name_modifier="";
        if(data.getText().toString().trim().isEmpty())
        {
            isvalidname = false;
            data.setError("Required field");
        }else if(data.getText().toString().length()>=32) {
            isvalidname = true;
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
            name = name.trim();
        }else if(!m.find()){
            isvalidname = false;
            data.setError("Invalid type name should contain only letters and spaces");
        }else{
            isvalidname = true;
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
    private void validatebal(EditText etbalance) {
        balance = etbalance.getText().toString();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(balance);
        isvalidbal = m.find();
        if(balance.isEmpty())
        {
            isvalidbal = false;
            etbalance.setError("Required field");
        }else{
            if(!isvalidbal)
            {
                etbalance.setError("Invalid balance");
            }
        }
    }
    public static boolean isValid(long number)
    {
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    public static int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    public static int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    public static int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    public static boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    public static int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }
    public static long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}