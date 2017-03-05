package com.restaurant.partnerapp.base;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by vJ on 2/8/17.
 */

public class BasePresenter<T extends IMvpView> implements IMvpPresenter<T> {

    private T view;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
        subscriptions.clear();
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public T getView() {
        return view;
    }

    public void addSubscription(Subscription subscription) {
        this.subscriptions.add(subscription);
    }

    public ViewNotAttachedException checkViewAttached(){
        return new ViewNotAttachedException();
    }

    private static class ViewNotAttachedException extends RuntimeException{
        public ViewNotAttachedException() {
            super("Please call Presenter.attachView(mvpView) before using Presenter");
        }
    }
}
