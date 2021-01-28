package com.impact.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.impact.credopayment.Api.JSONSchema.InitiateSchema;
import com.impact.credopayment.Api.JSONSchema.VerifySchema;
import com.impact.credopayment.Transactions;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    Transactions transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            transactions = new Transactions("ikdsfdhfieerer9er", "$iddkfdfhfdjddhdfd");
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

           /* transactions.verifyTransaction("12345", new Transactions.VerifyCallBack() {
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
            });*/
        }

    }
}