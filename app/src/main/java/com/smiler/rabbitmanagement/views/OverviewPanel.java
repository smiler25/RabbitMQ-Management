package com.smiler.rabbitmanagement.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smiler.rabbitmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewPanel extends LinearLayout {
    @BindView(R.id.panel_title)
    TextView title;
    @BindView(R.id.panel_value)
    TextView value;

    public OverviewPanel(Context context) {
        super(context);
    }

    public OverviewPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.overview_panel, this);
        ButterKnife.bind(this);

//        setBackgroundResource(R.drawable.background);
//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OverviewPanel, 0, 0);
//        try {
//            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
//            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
//        } finally {
//            a.recycle();
//        }

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        title.setText(R.string.title);
        value.setText(R.string.value);
    }

    public OverviewPanel(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public OverviewPanel setTitle(String value) {
        title.setText(value);
        return this;
    }

    public OverviewPanel setValue(String value) {
        this.value.setText(value);
        return this;
    }
}
