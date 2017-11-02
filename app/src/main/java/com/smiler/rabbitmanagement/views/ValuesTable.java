package com.smiler.rabbitmanagement.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.TableRowValue;

import java.util.ArrayList;


public class ValuesTable extends LinearLayout {
    public ValuesTable(Context context) {
        super(context);
    }

    public ValuesTable(Context context, ArrayList<TableRowValue> data) {
        super(context);
        addView(initView(context, data));
    }

    @NonNull
    public TableLayout getTable(Context context) {
        TableLayout table = new TableLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        table.setStretchAllColumns(true);
        table.setBackground(getResources().getDrawable(R.drawable.table_shape));

        int dpValue = 5; // margin in dips
        float d = context.getResources().getDisplayMetrics().density;
        int margin = (int)(dpValue * d); // margin in pixels
        params.setMargins(margin, margin, margin, margin);
        table.setLayoutParams(params);
        return table;
    }

    private View initView(Context context, ArrayList<TableRowValue> data) {
        TableLayout table = getTable(context);
        int line = 0;
        for (TableRowValue row: data) {
            table.addView(new ValuesTableRow(context, row.getTitle(), row.getValue(), (line & 1) == 0));
            line++;
        }
        return table;
    }
}
