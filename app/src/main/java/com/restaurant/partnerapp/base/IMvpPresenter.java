package com.restaurant.partnerapp.base;

/**
 * Created by vJ on 2/8/17.
 */

public interface IMvpPresenter<T extends IMvpView> {

    public void attachView(T view);

    public void detachView();

    public boolean isViewAttached();

    public T getView();
}
