package com.example.hp.justhealth.Common;

import com.example.hp.justhealth.Model.User;
import com.example.hp.justhealth.Retrofit.IMyAPI;
import com.example.hp.justhealth.Retrofit.RetrofitClient;

public class Common {

    public static final String BASE_URL = "http://192.168.43.85/justHealthWebService/App/";
    public static User currentUser = null;

    public static IMyAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IMyAPI.class);
    }

}
