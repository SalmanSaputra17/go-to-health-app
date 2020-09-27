package com.example.hp.gotohealth.Common;

import com.example.hp.gotohealth.Model.User;
import com.example.hp.gotohealth.Retrofit.APIService;
import com.example.hp.gotohealth.Retrofit.RetrofitClient;

public class Common {

    public static final String BASE_URL = "http://localhost:8000";

    public static User currentUser = null;

    public static APIService getAPI() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
