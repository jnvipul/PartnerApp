package com.restaurant.partnerapp.tables.interactors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.restaurant.partnerapp.ApplicationState;
import com.restaurant.partnerapp.customer.models.Customer;
import com.restaurant.partnerapp.database.DBHelper;
import com.restaurant.partnerapp.database.TableContract;
import com.restaurant.partnerapp.tables.network.TableDataService;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by vJ on 3/6/17.
 */
public class TableListInteractor {

    TableDataService service;

    public TableListInteractor(TableDataService service) {
        this.service = service;
    }

    public Observable<List<Boolean>> fetchTablesFromInternet() {
        return service.fetchTablesData();
    }

    public Observable<Cursor> getTablesFromDB(SQLiteDatabase database) {
        return Observable.fromCallable(new Callable<Cursor>() {

            @Override
            public Cursor call() throws Exception {
                return database.query(TableContract.TableAvailability.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
        });
    }

    public Observable<Long> addTablesToDatabase(List<Boolean> data, SQLiteDatabase database) {
        return Observable.from(data)
                .flatMap(new Func1<Boolean, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Boolean table) {
                        ContentValues cv = new ContentValues();
                        cv.put(TableContract.TableAvailability.AVAILABLE, table == true ? 1 : 0);
                        return Observable.just(new Long(database.insert(TableContract.TableAvailability.TABLE_NAME, null, cv)));
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
