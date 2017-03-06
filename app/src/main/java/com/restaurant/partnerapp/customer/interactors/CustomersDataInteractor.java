package com.restaurant.partnerapp.customer.interactors;

import android.os.Looper;

import com.restaurant.partnerapp.customer.models.Customer;
import com.restaurant.partnerapp.customer.network.CustomerListService;
import com.restaurant.partnerapp.utility.Logger;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by vJ on 3/6/17.
 */

public class CustomersDataInteractor {

    CustomerListService service;

    public CustomersDataInteractor(CustomerListService service) {
        this.service = service;
    }

    public Observable<List<Customer>> fetchCustomerDataInteractor(){
        Logger.debug("Main Thread", (Looper.getMainLooper().isCurrentThread()) + "");
        return service.loadCustomerData();
    }
}
