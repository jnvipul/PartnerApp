package com.restaurant.partnerapp.tables.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant.partnerapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vJ on 3/6/17.
 */

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.TableViewHolder> {

    List<Boolean> mDataSet;
    Context mContext;
    private RecyclerView mRecyclerView;

    public TableListAdapter(Context context, List<Boolean> mDataSet) {
        this.mDataSet = mDataSet;
        this.mContext = context;
    }

    @Override
    public TableListAdapter.TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_list, parent, false);


        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TableListAdapter.TableViewHolder holder, int position) {
        boolean isAvailable = mDataSet.get(position);
        holder.text.setText(isAvailable + "");
        if (isAvailable) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.cardview_shadow_start_color));
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    // View Holder
    public class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text)
        TextView text;

        public TableViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = mRecyclerView.getChildLayoutPosition(view);
            Toast.makeText(mContext, mDataSet.get(position) + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
}
