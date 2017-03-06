package com.restaurant.partnerapp.tables.presenters;

import com.restaurant.partnerapp.base.BasePresenter;
import com.restaurant.partnerapp.tables.interactors.TableListInteractor;
import com.restaurant.partnerapp.tables.network.TableDataService;
import com.restaurant.partnerapp.tables.ui.ITableListView;
import com.restaurant.partnerapp.utility.GsonUtil;
import com.restaurant.partnerapp.utility.Logger;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vJ on 3/6/17.
 */
public class TableListPresenter extends BasePresenter<ITableListView>{

    TableListInteractor interactor;

    public TableListPresenter(TableDataService service) {
        interactor = new TableListInteractor(service);
    }

    public void loadTablesData(){
        getView().showProgressBar();
        addSubscription(interactor.loadTablesData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadFailure));
    }

    private void onLoadSuccess(List<Boolean> data) {
        getView().hideProgressBar();
        Logger.debug(GsonUtil.dumpObject(data));
        getView().showTableList(data);
    }

    private void onLoadFailure(Throwable throwable) {
        getView().hideProgressBar();
        Logger.debug("Failed", throwable.getMessage());
    }
}
