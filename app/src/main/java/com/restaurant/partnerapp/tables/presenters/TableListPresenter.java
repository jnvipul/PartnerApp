package com.restaurant.partnerapp.tables.presenters;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.restaurant.partnerapp.base.BasePresenter;
import com.restaurant.partnerapp.tables.interactors.TableListInteractor;
import com.restaurant.partnerapp.tables.network.TableDataService;
import com.restaurant.partnerapp.tables.ui.ITableListView;
import com.restaurant.partnerapp.utility.GsonUtil;
import com.restaurant.partnerapp.utility.Logger;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by vJ on 3/6/17.
 */
public class TableListPresenter extends BasePresenter<ITableListView> {

    private final SQLiteDatabase database;
    TableListInteractor interactor;

    public TableListPresenter(TableDataService service, SQLiteDatabase db) {
        interactor = new TableListInteractor(service);
        this.database = db;
    }

    private void fetchTablesFromInternet() {
        addSubscription(interactor.fetchTablesFromInternet()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoadSuccess, this::onLoadFailure));
    }

    public void getTablesFromDatabase() {
        getView().showProgressBar();
        addSubscription(interactor.getTablesFromDB(database)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::fetchTablesFromInternetIfRequired));
    }

    private void fetchTablesFromInternetIfRequired(Cursor cursor) {
        Logger.debug("Table Count: " + cursor.getCount());
        if (cursor.getCount() == 0) {
            fetchTablesFromInternet();
        } else {
            getView().hideProgressBar();
            Logger.debug("Returning cursor");
            getView().showTableList(cursor);
        }
    }

    private void onLoadSuccess(List<Boolean> data) {
        addSubscription(interactor.addTablesToDatabase(data, database)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {

                    }
                }, this::onLoadFailure, this::getTablesFromDatabase));
    }

    private void onLoadFailure(Throwable throwable) {
        getView().hideProgressBar();
        Logger.debug("Failed", throwable.getMessage());
    }
}
