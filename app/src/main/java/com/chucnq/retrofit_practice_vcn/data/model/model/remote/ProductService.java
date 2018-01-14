package com.chucnq.retrofit_practice_vcn.data.model.model.remote;

import com.chucnq.retrofit_practice_vcn.data.model.model.model.ListProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Chức Ngô on 1/13/2018.
 */

public interface ProductService {

    @GET("/Product/GetListProducts")
    Call<ListProductResponse> getProducts();

}
