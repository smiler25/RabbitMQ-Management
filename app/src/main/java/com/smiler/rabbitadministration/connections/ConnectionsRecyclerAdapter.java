package com.smiler.rabbitadministration.connections;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.BaseRecyclerAdapter;

import butterknife.BindView;
import lombok.Getter;


public class ConnectionsRecyclerAdapter extends BaseRecyclerAdapter<Connection, ConnectionsRecyclerAdapter.ViewHolder> {
    ConnectionsRecyclerAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.connections_list_row, viewGroup, false);
        return new ViewHolder(v);
    }

    static class ViewHolder extends BaseRecyclerAdapter.ViewHolder<Connection> {
        @Getter
        protected Connection item;
        @BindView(R.id.con_name) TextView name;
        @BindView(R.id.con_vhost) TextView vhost;
        @BindView(R.id.con_user) TextView user;
        @BindView(R.id.con_state) TextView state;
        @BindView(R.id.con_channels) TextView channels;

        ViewHolder(View v) {
            super(v);
        }

        @SuppressLint("SetTextI18n")
        public void setView(Connection value) {
            item = value;
            name.setText(value.getName());
            vhost.setText(value.getVhost());
            user.setText(value.getUser());
            state.setText(value.getState());
            channels.setText(value.getChannels());
        }
    }
}
