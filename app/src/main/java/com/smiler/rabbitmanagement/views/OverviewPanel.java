package com.smiler.rabbitmanagement.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smiler.rabbitmanagement.R;

public class OverviewPanel extends LinearLayout {
    private TextView title;
    private TextView value;

    public OverviewPanel(Context context) {
        super(context);
    }

    public OverviewPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.overview_panel, this);

//        setBackgroundResource(R.drawable.background);

//        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OverviewPanel, 0, 0);
//        try {
//            mShowText = a.getBoolean(R.styleable.PieChart_showText, false);
//            mTextPos = a.getInteger(R.styleable.PieChart_labelPosition, 0);
//        } finally {
//            a.recycle();
//        }

//        String titleText = a.getString(R.styleable.ColorOptionsView_titleText);
//        @SuppressWarnings("ResourceAsColor")
//        int valueColor = a.getColor(R.styleable.ColorOptionsView_valueColor,
//                android.R.color.holo_blue_light);
//        a.recycle();
//
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        title = findViewById(R.id.panel_title);
        value = findViewById(R.id.panel_value);
        title.setText("titleText");
        value.setText("123400");


    }

    public OverviewPanel(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

}
