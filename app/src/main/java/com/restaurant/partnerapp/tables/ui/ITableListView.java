package com.restaurant.partnerapp.tables.ui;

import android.database.Cursor;

import com.restaurant.partnerapp.base.IMvpView;

/**
 * Created by vJ on 3/6/17.
 */
public interface ITableListView extends IMvpView {

    void showProgressBar();

    void hideProgressBar();

    void showTableList(Cursor data);

    void showLoadError(Throwable throwable);

}
