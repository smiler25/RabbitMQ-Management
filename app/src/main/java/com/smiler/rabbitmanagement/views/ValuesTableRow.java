package com.smiler.rabbitmanagement.views;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import com.smiler.rabbitmanagement.R;


class ValuesTableRow extends TableRow {

    public ValuesTableRow(Context context) {
        super(context);
        inflate(context, R.layout.table_row_name_value, this);
    }

    ValuesTableRow(Context context, String title, String value, boolean even) {
        super(context);
        inflate(context, R.layout.table_row_name_value, this);
        ((TextView) findViewById(R.id.row_title)).setText(title);
        ((TextView) findViewById(R.id.row_value)).setText(value);
        setBackground(even ? getResources().getDrawable(R.drawable.table_row_odd) : getResources().getDrawable(R.drawable.table_row_even));
    }
}
