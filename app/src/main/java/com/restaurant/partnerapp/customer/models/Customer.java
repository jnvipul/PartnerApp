package com.restaurant.partnerapp.customer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonSyntaxException;
import com.restaurant.partnerapp.utility.GsonUtil;

/**
 * Created by vJ on 3/6/17.
 */

public class Customer implements Parcelable {
    String customerFirstName;
    String customerLastName;
    long id;

    public Customer(String customerFirstName, String customerLastName, long id) {
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.id = id;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public static final Parcelable.Creator<Customer> CREATOR
            = new Parcelable.Creator<Customer>() {

        @Override
        public Customer createFromParcel(final Parcel parcel) {
            return fromJsonString(parcel.readString());
        }

        @Override
        public Customer[] newArray(final int i) {
            return new Customer[0];
        }
    };

    @Override
    public void writeToParcel(final Parcel parcel, final int flag) {
        parcel.writeString(toJsonString());
    }

    public String toJsonString() {
        return GsonUtil.getGson().toJson(this, Customer.class);
    }

    public static Customer fromJsonString(final String json) {
        try {
            return GsonUtil.getGson().fromJson(json, Customer.class);
        } catch (final JsonSyntaxException e) {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
