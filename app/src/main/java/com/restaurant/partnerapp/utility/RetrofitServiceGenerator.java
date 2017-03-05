package com.restaurant.partnerapp.utility;

import com.restaurant.partnerapp.customer.network.CustomerListService;

import retrofit2.Retrofit;

/**
 * Created by vJ on 3/6/17.
 */

public class RetrofitServiceGenerator {

    public static CustomerListService getCustomerListService(Retrofit retrofit){
        return retrofit.create(CustomerListService.class);
    }
}
