package com.restaurant.partnerapp.tables.ui;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.restaurant.partnerapp.ApplicationState;
import com.restaurant.partnerapp.R;
import com.restaurant.partnerapp.base.BaseActivity;
import com.restaurant.partnerapp.tables.presenters.TableListPresenter;
import com.restaurant.partnerapp.utility.RetrofitServiceGenerator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableListActivity extends BaseActivity implements ITableListView {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView list;

    TableListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);
        setup();
    }

    private void setup() {
        ButterKnife.bind(this);

        list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        list.setLayoutManager(layoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(),
//                layoutManager.getOrientation());
//        list.addItemDecoration(dividerItemDecoration);

        // presenter
        presenter = new TableListPresenter(RetrofitServiceGenerator.getTableDataService(ApplicationState.getInstance().getRetrofit()));
        presenter.attachView(this);
        presenter.loadTablesData();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showTableList(List<Boolean> data) {
        list.setAdapter(new TableListAdapter(this, data));
    }

    @Override
    public void showLoadError(Throwable throwable) {

    }
}
