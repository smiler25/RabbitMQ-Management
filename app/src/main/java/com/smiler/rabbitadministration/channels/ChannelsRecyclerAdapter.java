package com.smiler.rabbitadministration.channels;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.BaseRecyclerAdapter;

import butterknife.BindView;
import lombok.Getter;


public class ChannelsRecyclerAdapter extends BaseRecyclerAdapter<Channel, ChannelsRecyclerAdapter.ViewHolder> {
    ChannelsRecyclerAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.channels_list_row, viewGroup, false);
        return new ViewHolder(v);
    }

    static class ViewHolder extends BaseRecyclerAdapter.ViewHolder<Channel> {
        @Getter
        protected Channel item;
        @BindView(R.id.ch_name) TextView name;
        @BindView(R.id.ch_vhost) TextView vhost;
        @BindView(R.id.ch_user) TextView user;
        @BindView(R.id.ch_state) TextView state;
        @BindView(R.id.ch_prefetch) TextView prefetch;

        ViewHolder(View v) {
            super(v);
        }

        @SuppressLint("SetTextI18n")
        public void setView(Channel value) {
            item = value;
            name.setText(value.getName());
            vhost.setText(value.getVhost());
            user.setText(value.getUser());
            state.setText(value.getState());
            prefetch.setText(value.getPrefetchCount());
        }
    }
}
