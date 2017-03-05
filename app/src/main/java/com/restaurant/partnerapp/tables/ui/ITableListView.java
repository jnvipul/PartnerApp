package com.restaurant.partnerapp.tables.ui;

import com.restaurant.partnerapp.base.IMvpView;

import java.util.List;

/**
 * Created by vJ on 3/6/17.
 */
public interface ITableListView extends IMvpView {

    void showProgressBar();

    void hideProgressBar();

    void showTableList(List<Boolean> data);

    void showLoadError(Throwable throwable);

}
