package com.impact.credopayment.Api.Services;

import com.impact.credopayment.Api.JSONSchema.InitiateSchema;
import com.impact.credopayment.Api.JSONSchema.ThirdPartySchema;
import com.impact.credopayment.Api.JSONSchema.VerifyCardSchema;
import com.impact.credopayment.Api.JSONSchema.VerifySchema;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @Headers({"Accept:application/json", "Content-Type:application/json;"})
    @POST("payments/initiate")
    public Call<InitiateSchema> initiatePayment(
            @Header("Authorization") String publicKey,
            @Body RequestBody body

    );

    @GET("transactions/{transRef}/verify")
    public Call<VerifySchema> verifyTransactions(
            @Header("Authorization") String secretKey,
            @Path(value = "transRef", encoded = true) String transRef);

    @POST("payments/card/third-party/pay")
    @FormUrlEncoded
    public Call<ThirdPartySchema> thirdPartyPay(
            @Header("Authorization") String secretKey,
            @Field("orderAmount") Double orderAmount,
            @Field("orderCurrency") String currency,
            @Field("cardNumber") String cardNumber,
            @Field("expiryMonth") String expiryMonth,
            @Field("expiryYear") String expiryYear,
            @Field("securityCode") String securityCode,
            @Field("transRef") String transRef,
            @Field("customerEmail") String customerEmail,
            @Field("customerName") String customerName,
            @Field("customerPhone") String customerPhone
    );

    @POST("payments/card/third-party/3ds-verify-card-number")
    @FormUrlEncoded
    public Call<VerifyCardSchema> verifyCardNumber(
            @Header("Authorization") String secretKey,
            @Field("cardNumber") String cardNumber,
            @Field("orderCurrency") String orderCurrency
    );

    @FormUrlEncoded
    @POST("payments/card/third-party/3ds-pay")
    public Call<VerifyCardSchema> payThreeDs(
            @Header("Authorization") String secretKey,
            @Field("amount") double amount,
            @Field("currency") String currency,
            @Field("redirectUrl") String redirectUrl,
            @Field("transRef") String transRef,
            @Field("paymentOptions") String paymentOptions,
            @Field("customerEmail") String customerEmail,
            @Field("customerName") String customerName,
            @Field("customerPhone") String customerPhone
    );

}
