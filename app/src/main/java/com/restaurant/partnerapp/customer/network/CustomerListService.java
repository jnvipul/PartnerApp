package com.restaurant.partnerapp.customer.network;

import com.restaurant.partnerapp.customer.models.Customer;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by vJ on 3/6/17.
 */

public interface CustomerListService {

    @GET("quandoo-assessment/customer-list.json")
    Observable<List<Customer>> loadCustomerData();
}
