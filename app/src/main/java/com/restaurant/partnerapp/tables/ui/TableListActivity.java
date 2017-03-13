package com.restaurant.partnerapp.tables.ui;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.restaurant.partnerapp.ApplicationState;
import com.restaurant.partnerapp.R;
import com.restaurant.partnerapp.base.BaseActivity;
import com.restaurant.partnerapp.customer.models.Customer;
import com.restaurant.partnerapp.database.DBHelper;
import com.restaurant.partnerapp.tables.presenters.TableListPresenter;
import com.restaurant.partnerapp.utility.RetrofitServiceGenerator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableListActivity extends BaseActivity implements ITableListView {

    public static final String KEY_CUSTOMER = "KEY-CUSTOMER";

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recycler_view)
    RecyclerView list;

    TableListPresenter presenter;
    private Customer mCustomer;

    private TableListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);
        setup();
    }

    private void setup() {
        ButterKnife.bind(this);

        // Get Customer Info
        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey(KEY_CUSTOMER)) {
            mCustomer = bundle.getParcelable(KEY_CUSTOMER);
        }

        list.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        list.setLayoutManager(layoutManager);

        // presenter
        SQLiteDatabase database = DBHelper.getInstance(ApplicationState.getInstance()).getWritableDatabase();
        presenter = new TableListPresenter(RetrofitServiceGenerator
                .getTableDataService(ApplicationState.getInstance().getRetrofit()), database);
        presenter.attachView(this);
        presenter.getTablesFromDatabase();
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
    public void showTableList(Cursor data) {
        mAdapter = new TableListAdapter(this, data);
        list.setAdapter(mAdapter);
    }

    @Override
    public void showLoadError(Throwable throwable) {

    }

    @Override
    public void updateReservationStatuses(Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    public void showAlreadyReservedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

        builder.setMessage(R.string.already_reserved)
                .setTitle(R.string.oops)
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do Nothing
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void showReservationDialog(int rowId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);

        builder.setMessage(R.string.reservation_confermation_text)
                .setTitle(R.string.reservation_confirmation_header)
                .setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // update details in the table
                        presenter.requestTableReservation(rowId, mCustomer);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do Nothing
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
