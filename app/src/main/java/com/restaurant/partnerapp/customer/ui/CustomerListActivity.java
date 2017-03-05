package com.restaurant.partnerapp.customer.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.restaurant.partnerapp.ApplicationState;
import com.restaurant.partnerapp.R;
import com.restaurant.partnerapp.base.BaseActivity;
import com.restaurant.partnerapp.customer.models.Customer;
import com.restaurant.partnerapp.customer.presenters.CustomerListPresenter;
import com.restaurant.partnerapp.utility.RetrofitServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerListActivity extends BaseActivity implements ICustomerListView {

    @BindView(R.id.list)
    ListView listView;

    @BindView(R.id.progressBar)
    ProgressBar progressbar;

    CustomerListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        setup();
    }

    private void setup() {

        // Butter Knife
        ButterKnife.bind(this);

        // presenter
        // TODO : Use dagger to get retrofit
        presenter = new CustomerListPresenter(RetrofitServiceGenerator.getCustomerListService(ApplicationState.getInstance().getRetrofit()));
        presenter.attachView(this);
        presenter.loadCustomerData();
    }

    @Override
    public void showProgressBar() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showCustomerList(List<Customer> data) {

    }

    @Override
    public void showLoadError(Throwable throwable) {

    }
}
