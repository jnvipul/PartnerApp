package com.restaurant.partnerapp.tables.network;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by vJ on 3/6/17.
 */

public interface TableDataService {

    @GET("quandoo-assessment/table-map.json")
    Observable<List<Boolean>> fetchTablesData();

}
