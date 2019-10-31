package com.smiler.rabbitadministration.queues.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smiler.rabbitadministration.R;

import java.util.List;

import androidx.annotation.NonNull;


public class FiltersListAdapter extends ArrayAdapter<Filter> {
    private String infoFormat = "(regex)";

    FiltersListAdapter(Context context, List<Filter> filters) {
        super(context, 0, filters);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Filter filter = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_profile_list_item, parent, false);
        }

        TextView text = convertView.findViewById(R.id.list_text_main);
        TextView textInfo = convertView.findViewById(R.id.list_text_small);
        if (filter != null && text != null) {
            text.setText(filter.getValue());
            if (filter.isRegex()) {
                textInfo.setText(infoFormat);
            }
        }
        return convertView;
    }
}