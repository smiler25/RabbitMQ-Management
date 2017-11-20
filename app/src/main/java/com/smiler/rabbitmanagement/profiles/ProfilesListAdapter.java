package com.smiler.rabbitmanagement.profiles;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smiler.rabbitmanagement.R;

import java.util.List;


public class ProfilesListAdapter extends ArrayAdapter<Profile> {

    private String infoFormat;

    ProfilesListAdapter(Context context, List<Profile> profiles) {
        super(context, 0, profiles);
        infoFormat = context.getString(R.string.profile_select_info);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Profile profile = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_profile_list_item, parent, false);
        }

        TextView text = convertView.findViewById(R.id.list_text_main);
        TextView textInfo = convertView.findViewById(R.id.list_text_small);
        if (profile != null && text != null) {
            text.setText(profile.getTitle());
            textInfo.setText(String.format(infoFormat, profile.getLogin(), profile.getHost()));
        }
        return convertView;
    }
}