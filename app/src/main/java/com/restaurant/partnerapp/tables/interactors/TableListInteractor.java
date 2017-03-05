package com.restaurant.partnerapp.tables.interactors;

import com.restaurant.partnerapp.tables.network.TableDataService;

import java.util.List;

import rx.Observable;

/**
 * Created by vJ on 3/6/17.
 */
public class TableListInteractor {

    TableDataService service;

    public TableListInteractor(TableDataService service) {
        this.service = service;
    }

    public Observable<List<Boolean>> loadTablesData() {
        return service.fetchTablesData();
    }
}
