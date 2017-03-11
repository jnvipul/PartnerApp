package com.restaurant.partnerapp.customer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.restaurant.partnerapp.ApplicationState;
import com.restaurant.partnerapp.ClearReservationsJobScheduler;
import com.restaurant.partnerapp.R;
import com.restaurant.partnerapp.base.BaseActivity;
import com.restaurant.partnerapp.customer.models.Customer;
import com.restaurant.partnerapp.customer.presenters.CustomerListPresenter;
import com.restaurant.partnerapp.tables.ui.TableListActivity;
import com.restaurant.partnerapp.utility.RetrofitServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerListActivity extends BaseActivity implements ICustomerListView {

    @BindView(R.id.recycler_view)
    RecyclerView list;

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

        // RecyclerView
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);

        // presenter
        // TODO : Use dagger to get retrofit
        presenter = new CustomerListPresenter(RetrofitServiceGenerator.getCustomerDataService(ApplicationState.getInstance().getRetrofit()));
        presenter.attachView(this);
        presenter.loadCustomerData();

        // Schedule job for clearing all reservations every 10 minutes
        ClearReservationsJobScheduler.scheduleClearReservationsJob(this);
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
        list.setAdapter(new CustomerListAdapter(this, data));
    }

    @Override
    public void showLoadError(Throwable throwable) {

    }

    public void onCustomerClick(Customer customer) {
        Intent intent = new Intent(this, TableListActivity.class);
        intent.putExtra(TableListActivity.KEY_CUSTOMER, customer);
        startActivity(intent);
    }
}
