package com.chucnq.retrofit_practice_vcn.data.model.model.remote;

/**
 * Created by User on 1/13/2018.
 */

public class APIUtils {

    public static final String BASE_URL = "http://test.vcncorp.net/";

    public static ProductService getProService(){
        return RetrofitClient.getClient(BASE_URL).create(ProductService.class);
    }

}
