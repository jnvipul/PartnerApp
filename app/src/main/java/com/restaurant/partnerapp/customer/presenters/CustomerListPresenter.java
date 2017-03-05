package com.restaurant.partnerapp.customer.presenters;

import android.os.Looper;

import com.restaurant.partnerapp.base.BasePresenter;
import com.restaurant.partnerapp.customer.interactors.CustomersDataInteractor;
import com.restaurant.partnerapp.customer.models.Customer;
import com.restaurant.partnerapp.customer.network.CustomerListService;
import com.restaurant.partnerapp.customer.ui.ICustomerListView;
import com.restaurant.partnerapp.utility.GsonUtil;
import com.restaurant.partnerapp.utility.Logger;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vJ on 3/6/17.
 */

public class CustomerListPresenter extends BasePresenter<ICustomerListView> {

    CustomersDataInteractor interactor;

    public CustomerListPresenter(CustomerListService service) {
        interactor = new CustomersDataInteractor(service);
    }

    public void loadCustomerData() {
        getView().showProgressBar();
        Logger.debug("Main Thread1", (Looper.getMainLooper().isCurrentThread()) + "");
        addSubscription(interactor.fetchCustomerDataInteractor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadFailure));

    }

    private void onLoadSuccess(List<Customer> data) {
        getView().hideProgressBar();
        Logger.debug("Main Thread2", (Looper.getMainLooper().isCurrentThread()) + "");
        Logger.debug("Inside onLoadSuccess");
        Logger.debug(GsonUtil.getGson().toJson(data));
        getView().showCustomerList(data);
    }

    private void onLoadFailure(Throwable throwable) {
        getView().hideProgressBar();
        getView().showLoadError(throwable);
        Logger.debug("Inside onLog Failure", throwable.getMessage());
    }
}
