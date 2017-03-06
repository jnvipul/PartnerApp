package com.restaurant.partnerapp.customer.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.restaurant.partnerapp.R;
import com.restaurant.partnerapp.customer.models.Customer;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vJ on 3/6/17.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    private List<Customer> mDataSet;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public CustomerListAdapter(Context context, List<Customer> mDataSet) {
        this.mDataSet = mDataSet;
        this.mContext = context;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new CustomerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_customer_list, null));

    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {

        Customer customer = mDataSet.get(position);

        holder.id.setText(customer.getId() + "");
        holder.name.setText(customer.getCustomerFirstName() + " " + customer.getCustomerLastName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.id)
        TextView id;

        public CustomerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = mRecyclerView.getChildLayoutPosition(view);
            ((CustomerListActivity)mContext).onCustomerClick(mDataSet.get(position));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }
}
