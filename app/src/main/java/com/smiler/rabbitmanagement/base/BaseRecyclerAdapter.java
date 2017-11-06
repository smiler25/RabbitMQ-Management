package com.smiler.rabbitmanagement.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.interfaces.BaseListListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;


public abstract class BaseRecyclerAdapter<T, VH extends BaseRecyclerAdapter.ViewHolder> extends RecyclerView.Adapter<VH> {
    @Setter
    private BaseListListener<T> listener;
    private ArrayList<T> data;

    public BaseRecyclerAdapter() {
        super();
    }

    @Override
    abstract public VH onCreateViewHolder(ViewGroup viewGroup, int viewType);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T value = data.get(position);
        holder.setView(value);
        holder.setCallback(listener);
        if((position % 2 == 0)){
            holder.root.setBackgroundResource(R.color.listEven);
        } else {
            holder.root.setBackgroundResource(R.color.listOdd);
        }
    }

    public void updateData(ArrayList<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    abstract public static class ViewHolder<T> extends RecyclerView.ViewHolder {
        final View root;
        @Getter
        protected T item;
        @Setter
        BaseListListener<T> callback;

        protected ViewHolder(View v) {
            super(v);
            root = v;
            ButterKnife.bind(this, root);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onListElementClick(getItem());
                    }
                }
            });
        }

        abstract public void setView(T value);
    }
}
