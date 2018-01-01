package com.smiler.rabbitadministration.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.interfaces.UpdatableFragmentListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;


abstract public class DetailFragment<T> extends Fragment {
    @Setter
    private T data;

    @BindView(R.id.container_info)
    protected LinearLayout infoContainer;

    @Setter @Nullable
    protected UpdatableFragmentListener callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.common_detail, container, false);
        ButterKnife.bind(this, root);
        setView(data);
        return root;
    }

    abstract protected void setView(final T data);
}
