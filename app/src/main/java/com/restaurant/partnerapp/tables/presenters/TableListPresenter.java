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
        addSubscription(interactor.loadTablesData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadFailure));
    }

    private void onLoadSuccess(List<Boolean> tables) {
        Logger.debug(GsonUtil.dumpObject(tables));
    }

    private void onLoadFailure(Throwable throwable) {
        Logger.debug("Failed");
    }
}
