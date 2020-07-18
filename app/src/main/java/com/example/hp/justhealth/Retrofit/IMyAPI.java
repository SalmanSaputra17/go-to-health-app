package com.example.hp.justhealth.Retrofit;

import com.example.hp.justhealth.Model.APIResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IMyAPI {

    @FormUrlEncoded
    @POST("login.php")
    Call<APIResponse> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<APIResponse> registerUser(@Field("username") String username, @Field("jk") String jk, @Field("tgl_lahir") String tgl_lahir,
                                   @Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("checkBB.php")
    Call<APIResponse> checkBBUser(@Field("berat_badan") double berat_badan, @Field("tinggi_badan") double tinggi_badan);

    @FormUrlEncoded
    @POST("dayCalori.php")
    Call<APIResponse> dayCalori(@Field("jk") String jk, @Field("tgl_lahir") String tgl_lahir, @Field("berat_badan") double berat_badan,
                                @Field("tinggi_badan") double tinggi_badan, @Field("level_aktifitas") String level_aktifitas);

    @FormUrlEncoded
    @POST("foodCalori.php")
    Call<APIResponse> foodCalori(@Field("makanan") String makanan);

    @FormUrlEncoded
    @POST("DN.php")
    Call<APIResponse> DN(@Field("tgl_lahir") String tgl_lahir);

    @FormUrlEncoded
    @POST("getUser.php")
    Call<APIResponse> getUserInfomartion(@Field("email") String email);

    @FormUrlEncoded
    @POST("deleteUser.php")
    Call<APIResponse> deleteUserAccount(@Field("email") String email);


}
