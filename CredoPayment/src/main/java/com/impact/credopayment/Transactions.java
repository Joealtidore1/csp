package com.impact.credopayment;

import com.impact.credopayment.Api.JSONSchema.InitiateSchema;
import com.impact.credopayment.Api.JSONSchema.ThirdPartySchema;
import com.impact.credopayment.Api.JSONSchema.VerifyCardSchema;
import com.impact.credopayment.Api.JSONSchema.VerifySchema;
import com.impact.credopayment.Api.Services.ApiClient;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Transactions {

    private final String publicKey;
    private final String secretKey;

    ApiClient apiClient;

    public Transactions(String publicKey, String secretKey) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
        this.apiClient = new ApiClient();
    }


    /**
     * @param amount the amount to be paid
     * @param currency the currency to perform transaction in
     * @param redirectUrl
     * @param transRef a transaction reference
     * @param paymentOptions payment options which can be CARD, BANK OR USSD
     * @param customerName The name of customer performing the transaction
     * @param customerEmail The email of customer performing the transaction
     * @param customerPhone The phone number of customer performing the transaction
     * @param customCallback a call back object to return response to user
     */
    public  void initiatePayment(Double amount, String currency, String redirectUrl, String transRef, String paymentOptions, String customerName, String customerEmail, String customerPhone, InitiatePaymentCallBack customCallback){

        Call<InitiateSchema> call = apiClient.getApi().initiatePayment(publicKey, amount, currency, redirectUrl, transRef, paymentOptions, customerEmail, customerName, customerPhone);
        call.enqueue(new Callback<InitiateSchema>() {
            @Override
            public void onResponse(Call<InitiateSchema> call, Response<InitiateSchema> response) {
                customCallback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<InitiateSchema> call, Throwable t) {
                customCallback.onFailure(t);
            }
        });
    }

    /**
     * @param transRef the transaction reference used to initialize payment
     * @param verifyCallBack a callback object to return response to user
     */
    public void verifyTransaction(String transRef, VerifyCallBack verifyCallBack){
        Call<VerifySchema> call = apiClient.getApi().verifyTransactions(this.secretKey, transRef);
        call.enqueue(new Callback<VerifySchema>() {
            @Override
            public void onResponse(Call<VerifySchema> call, Response<VerifySchema> response) {
                verifyCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VerifySchema> call, Throwable t) {
                verifyCallBack.onFailure(t);
            }
        });
    }

    /**
     * @param orderAmount amount for transaction
     * @param currency the currency for the transaction
     * @param cardNumber the transaction card number (e.g mastercard)
     * @param expiryMonth the expiry month of the card(between 1 to 12)
     * @param expiryYear the expiry year of the card
     * @param securityCode the security code of the card
     * @param transRef the transaction reference
     * @param customerEmail The email of customer performing the transaction
     * @param customerName The name of customer performing the transaction
     * @param customerPhone The phone number of customer performing the transaction
     * @param thirdPartyCallBack the callback object to return response to user
     */
    public void thirdPartyPay(Double orderAmount, String currency, String cardNumber, String expiryMonth, String expiryYear, String securityCode, String transRef, String customerEmail, String customerName, String customerPhone, ThirdPartyCallBack thirdPartyCallBack){
        Call<ThirdPartySchema> call = apiClient.getApi().thirdPartyPay(secretKey, orderAmount, currency, cardNumber, expiryMonth, expiryYear, securityCode, transRef, customerEmail, customerName, customerPhone);
        call.enqueue(new Callback<ThirdPartySchema>() {
            @Override
            public void onResponse(Call<ThirdPartySchema> call, Response<ThirdPartySchema> response) {
                thirdPartyCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ThirdPartySchema> call, Throwable t) {
                thirdPartyCallBack.onFailure(t);
            }
        });
    }


    /**
     * @param cardNumber the number on the card
     * @param orderCurrency the currency used for the order
     * @param verifyCardCallBack the callback object to get response for user
     */
    public void verifyCardNumber(String cardNumber, String orderCurrency, VerifyCardCallBack verifyCardCallBack){
        Call<VerifyCardSchema> call = apiClient.getApi().verifyCardNumber(secretKey, cardNumber, orderCurrency);
        call.enqueue(new Callback<VerifyCardSchema>() {
            @Override
            public void onResponse(Call<VerifyCardSchema> call, Response<VerifyCardSchema> response) {
                verifyCardCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VerifyCardSchema> call, Throwable t) {
                verifyCardCallBack.onFailure(t);
            }
        });
    }


    /**
     * @param amount
     * @param currency
     * @param redirectUrl
     * @param transRef
     * @param paymentOptions
     * @param customerEmail
     * @param customerName
     * @param customerPhone
     * @param verifyCardCallBack
     */
    public void payThreeDs(double amount, String currency, String redirectUrl, String transRef, String paymentOptions, String customerEmail, String customerName, String customerPhone, VerifyCardCallBack verifyCardCallBack){
        Call<VerifyCardSchema> call = apiClient.getApi().payThreeDs(secretKey, amount, currency, redirectUrl, transRef, paymentOptions, customerEmail, customerName, customerPhone);
        call.enqueue(new Callback<VerifyCardSchema>() {
            @Override
            public void onResponse(Call<VerifyCardSchema> call, Response<VerifyCardSchema> response) {
                verifyCardCallBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<VerifyCardSchema> call, Throwable t) {
                verifyCardCallBack.onFailure(t);
            }
        });
    }



    public interface InitiatePaymentCallBack{
        void onSuccess(InitiateSchema schema);
        void onFailure(Throwable t);
    }
    public interface VerifyCardCallBack{
        void onSuccess(VerifyCardSchema schema);
        void onFailure(Throwable t);
    }
    public interface VerifyCallBack{
        void onSuccess(VerifySchema schema);
        void onFailure(Throwable t);
    }
    public interface ThirdPartyCallBack{
        void onSuccess(ThirdPartySchema schema);
        void onFailure(Throwable t);
    }

}