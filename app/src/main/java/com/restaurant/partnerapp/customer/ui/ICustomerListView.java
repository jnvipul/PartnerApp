package com.restaurant.partnerapp.customer.ui;

import com.restaurant.partnerapp.base.IMvpView;
import com.restaurant.partnerapp.customer.models.Customer;

import java.util.List;

/**
 * Created by vJ on 3/6/17.
 */

public interface ICustomerListView extends IMvpView{

    void showProgressBar();

    void hideProgressBar();

    void showCustomerList(List<Customer> data);

    void showLoadError(Throwable throwable);
}
