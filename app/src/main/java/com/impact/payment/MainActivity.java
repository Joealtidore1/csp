package com.impact.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.impact.credopayment.Api.JSONSchema.InitiateSchema;
import com.impact.credopayment.Api.JSONSchema.VerifySchema;
import com.impact.credopayment.Transactions;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    Transactions transactions;
    private ArrayList<CustomItem> customItem, cardTypeList;
    Spinner paymentOptions, cardType;
    EditText cardExpiry, cvv, cardNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paymentOptions = findViewById(R.id.paymentOptions);
        cardType = findViewById(R.id.cardType);
        customItem = new ArrayList<>();
        cardTypeList = new ArrayList<>();

        cardTypeList.add(new CustomItem("Visa"));
        cardTypeList.add(new CustomItem("Master Card"));
        cardTypeList.add(new CustomItem("Verve"));

        customItem.add(new CustomItem("Card"));
        customItem.add(new CustomItem("Bank"));
        customItem.add(new CustomItem("USSD"));

        cardNumber = findViewById(R.id.cardNumber);
        cvv = findViewById(R.id.cardCVV);
        cardExpiry = findViewById(R.id.cardExpiry);
        cardExpiry.addTextChangedListener(new TextWatcher() {
            boolean del = false;
            int currentLength = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                currentLength = cardExpiry.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(cardExpiry.length() == 2 && currentLength < cardExpiry.length())
                    cardExpiry.append("/");
                if(cardExpiry.length() == 5){
                    cardExpiry.setFocusable(false);
                    cardNumber.setFocusable(false);
                    cvv.setFocusable(true);
                }
            }
        });

        cardExpiry.setOnTouchListener(this);
        cardNumber.setOnTouchListener(this);



        CustomAdapter adapter = new CustomAdapter(this, customItem);
        CustomAdapterCardType adapter2 = new CustomAdapterCardType(this, cardTypeList);

        cardType.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        paymentOptions.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        try {
            transactions = new Transactions("pk_demo-7BckFQAPo5nftWQDABrHi3kb8moGAr.qBgeBfvVGH-d", "sk_demo-Dj1rcY2rxNpmJgYwmhylIy7RpoS7Zy.ygUca22uTg-d");
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            transactions = null;
            e.printStackTrace();
            Log.d("INITIALIZATION ERROR", e.getMessage());
        }

        if (transactions != null){
            transactions.initiatePayment(56.0,
                    "NGN",
                    "https://nugitech.com",
                    "12345",
                    "CARD",
                    "Joseph Ofem",
                    "ofemJoseph1@gmail.com",
                    "0904634322", new Transactions.InitiatePaymentCallBack() {
                        @Override
                        public void onSuccess(InitiateSchema schema) {
                            if(schema != null) {
                                Log.d("SUCCESS MESSAGE", schema.getStatus());
                            }else{
                                Log.d("SUCCESS MESSAGE", "EMPTY MESSAGE");
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.d("FAILURE ERROR", t.getMessage());
                        }
                    });

            transactions.verifyTransaction("12345", new Transactions.VerifyCallBack() {
                @Override
                public void onSuccess(VerifySchema schema) {
                    if(schema == null) {
                        Log.d("SUCCESS MESSAGE", schema.getStatus());
                    }else{
                        Log.d("SUCCESS MESSAGE", "EMPTY MESSAGESSS");
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("FAILURE ERROR", t.getMessage());
                }
            });
        }

    }

    public void cardPay(View view) {
        startActivity(new Intent(MainActivity.this, VerifyAccount.class));
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(v.getId()){
            case R.id.cardExpiry:
                cardExpiry.setFocusable(true);
                cardExpiry.setFocusableInTouchMode(true);
                break;
            case R.id.cardNumber:
                cardNumber.setFocusable(true);
                cardNumber.setFocusableInTouchMode(true);
                break;
            case R.id.cardCVV:
                cvv.setFocusable(true);
                cvv.setFocusableInTouchMode(true);
                break;

        }
        return false;
    }
}