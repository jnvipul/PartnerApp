package com.restaurant.partnerapp.utility;

import com.restaurant.partnerapp.customer.network.CustomerListService;
import com.restaurant.partnerapp.tables.network.TableDataService;

import retrofit2.Retrofit;

/**
 * Created by vJ on 3/6/17.
 */

public class RetrofitServiceGenerator {

    public static CustomerListService getCustomerDataService(Retrofit retrofit){
        return retrofit.create(CustomerListService.class);
    }

    public static TableDataService getTableDataService(Retrofit retrofit) {
        return retrofit.create(TableDataService.class);
    }
}
