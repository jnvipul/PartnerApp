package com.restaurant.partnerapp.customer.models;

import android.os.Parcel;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created by vJ on 3/11/17.
 */
public class CustomerAndroidTest {

    @RunWith(AndroidJUnit4.class)
    @SmallTest
    public class LogHistoryAndroidUnitTest {

        public static final String TEST_FIRST_NAME = "first_name";
        public static final String TEST_LAST_NAME = "last_name";
        public static final int TEST_ID = 123;

        private Customer mCustomer;

        @Before
        public void createLogHistory() {
            mCustomer = new Customer(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ID);
        }

        @Test
        public void logHistory_ParcelableWriteRead() {
            // Set up the Parcelable object to send and receive.

            // Write the data.
            Parcel parcel = Parcel.obtain();
            mCustomer.writeToParcel(parcel, mCustomer.describeContents());

            // After you're done with writing, you need to reset the parcel for reading.
            parcel.setDataPosition(0);

            // Read the data.
            Customer createdFromParcel = Customer.CREATOR.createFromParcel(parcel);

            // Verify that the received data is correct.
//            assertThat(createdFromParcelData.size(), is(1));
            assertThat(createdFromParcel.getCustomerFirstName(), is(TEST_FIRST_NAME));
            assertThat(createdFromParcel.getCustomerLastName(), is(TEST_LAST_NAME));
        }
    }


}