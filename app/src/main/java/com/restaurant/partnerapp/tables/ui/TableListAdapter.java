package com.restaurant.partnerapp.tables.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.restaurant.partnerapp.R;
import com.restaurant.partnerapp.base.CursorRecyclerViewAdapter;
import com.restaurant.partnerapp.database.TableContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vJ on 3/6/17.
 */

public class TableListAdapter extends CursorRecyclerViewAdapter<TableListAdapter.TableViewHolder> {

    private RecyclerView mRecyclerView;

    public TableListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public TableListAdapter.TableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_table_list, parent, false);
        return new TableViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TableListAdapter.TableViewHolder holder, Cursor cursor) {
        boolean isAvailable = getTableAvailability(cursor);
        holder.text.setText(isAvailable + "");
        if (isAvailable) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.cardview_shadow_start_color));
        }
    }

    private Boolean getTableAvailability(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        return cursor.getInt(cursor
                .getColumnIndex(TableContract.TableAvailability.AVAILABLE)) == 1 ? true : false;
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
            if (getCursor() != null) {
                int position = mRecyclerView.getChildLayoutPosition(view);
                Cursor cursor = getCursor();
                cursor.moveToPosition(position);
                boolean isAvailable = getTableAvailability(cursor);
                Toast.makeText(getContext(), isAvailable + " " + position, Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;
    }
}
