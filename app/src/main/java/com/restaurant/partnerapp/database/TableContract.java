package com.restaurant.partnerapp.database;

import android.provider.BaseColumns;

/**
 * Created by vJ on 3/7/17.
 */

public class TableContract {

    private TableContract(){

    }

    public static final class TableAvailability implements BaseColumns {

        public static final String TABLE_NAME = "table_availability";

        // Columns
        public static  final String AVAILABLE = "available";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String CUSTOMER_FIRST_NAME = "customer_first_name";
        public static final String CUSTOMER_LAST_NAME = "customer_last_name";
        public static final String TIME_STAMP = "time_stamp";


    }
}
