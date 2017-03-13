package com.restaurant.partnerapp.customer.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerListActivity extends BaseActivity implements ICustomerListView,
        SearchView.OnQueryTextListener {

    @BindView(R.id.recycler_view)
    RecyclerView list;

    @BindView(R.id.progressBar)
    ProgressBar progressbar;

    CustomerListPresenter presenter;
    private CustomerListAdapter mAdapter;
    private List<Customer> mCustomers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        setup();
    }

    private void setup() {

        // Butter Knife
        ButterKnife.bind(this);

        // toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Customers");
        setSupportActionBar(toolbar);

        // RecyclerView
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        mAdapter = new CustomerListAdapter(this, mCustomers);
        list.setAdapter(mAdapter);

        // presenter
        // TODO : Use dagger to get retrofit
        presenter = new CustomerListPresenter(RetrofitServiceGenerator.getCustomerDataService(ApplicationState.getInstance().getRetrofit()));
        presenter.attachView(this);
        presenter.loadCustomerData();

        // Schedule job for clearing all reservations every 10 minutes
        ClearReservationsJobScheduler.scheduleClearReservationsJob(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(this);
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView
                .findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Customer> filteredModelList = filter(mCustomers, query);
        mAdapter.replaceAll(filteredModelList);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<Customer> filter(List<Customer> customers, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Customer> filteredModelList = new ArrayList<>();
        for (Customer customer : customers) {
            final String text = customer.getCustomerFirstName().toLowerCase() + customer.getCustomerLastName().toLowerCase();
            ;
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(customer);
            }
        }
        return filteredModelList;
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
        this.mCustomers.addAll(data);
        mAdapter.notifyDataSetChanged();
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
