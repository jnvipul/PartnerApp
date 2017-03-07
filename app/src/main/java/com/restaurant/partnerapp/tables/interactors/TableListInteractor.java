package com.restaurant.partnerapp.tables.interactors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

}
