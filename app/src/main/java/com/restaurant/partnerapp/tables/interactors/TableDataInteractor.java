package com.restaurant.partnerapp.tables.interactors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;

import com.restaurant.partnerapp.ApplicationState;
import com.restaurant.partnerapp.customer.models.Customer;
import com.restaurant.partnerapp.database.DBHelper;
import com.restaurant.partnerapp.database.TableContract;
import com.restaurant.partnerapp.tables.network.TableDataService;
import com.restaurant.partnerapp.utility.Logger;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by vJ on 3/6/17.
 */
public class TableDataInteractor {

    TableDataService service;

    SQLiteDatabase mDatabase;

    public TableDataInteractor(TableDataService service, SQLiteDatabase database) {
        this.service = service;
        mDatabase = database;
    }

    public Observable<List<Boolean>> fetchTablesFromInternet() {
        return service.fetchTablesData();
    }

    public Observable<Cursor> getTablesFromDB() {
        return Observable.fromCallable(new Callable<Cursor>() {

            @Override
            public Cursor call() throws Exception {
                return mDatabase.query(TableContract.TableAvailability.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
        });
    }

    public Observable<Long> addTablesToDatabase(List<Boolean> data) {
        return Observable.from(data)
                .flatMap(new Func1<Boolean, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Boolean table) {
                        ContentValues cv = new ContentValues();
                        cv.put(TableContract.TableAvailability.AVAILABLE, table == true ? 1 : 0);
                        return Observable.just(new Long(mDatabase.insert(TableContract.TableAvailability.TABLE_NAME, null, cv)));
                    }
                });


    }

    public Observable<Cursor> makeTableReservation(int rowId, Customer customer) {
        return Observable.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                SQLiteDatabase db = DBHelper.getInstance(ApplicationState.getInstance()).getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(TableContract.TableAvailability.CUSTOMER_ID, customer.getId());
                cv.put(TableContract.TableAvailability.CUSTOMER_FIRST_NAME, customer.getCustomerFirstName());
                cv.put(TableContract.TableAvailability.CUSTOMER_LAST_NAME, customer.getCustomerLastName());
                cv.put(TableContract.TableAvailability.AVAILABLE, 0);
                int result = db.update(TableContract.TableAvailability.TABLE_NAME, cv, TableContract.TableAvailability._ID + "=" + rowId, null);
                return Observable.just(result);
            }
        }).flatMap(new Func1<Object, Observable<Cursor>>() {
            @Override
            public Observable<Cursor> call(Object o) {
                SQLiteDatabase database = DBHelper.getInstance(ApplicationState.getInstance()).getWritableDatabase();
                return Observable.just(database.query(TableContract.TableAvailability.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null));
            }
        });
    }
}
