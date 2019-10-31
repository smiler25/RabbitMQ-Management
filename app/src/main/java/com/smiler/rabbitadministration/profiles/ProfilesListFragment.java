package com.smiler.rabbitadministration.profiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.smiler.rabbitadministration.AppRepository;
import com.smiler.rabbitadministration.R;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import lombok.Setter;

public class ProfilesListFragment extends ListFragment {
    public static final String TAG = "BS-ProfilesListFragment";

    private ProfilesListAdapter adapter;
    @Setter @Nullable
    private ProfilesListListener listener;

    public interface ProfilesListListener {
        void onListElementClick(Profile profile);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);
        rootView.setTag(TAG);
        ArrayList<Profile> data = (ArrayList<Profile>) AppRepository.getInstance(getActivity().getApplicationContext()).getAllProfilesSync();
        adapter = new ProfilesListAdapter(getContext(), data);
        setListAdapter(adapter);
        ListView listView = rootView.findViewById(android.R.id.list);
        listView.setAdapter(adapter);
        return rootView;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Profile profile = adapter.getItem(position);
        if (listener != null && profile != null) {
            listener.onListElementClick(profile);
        }
    }

    public void refresh() {
        ArrayList<Profile> data = (ArrayList<Profile>) AppRepository.getInstance(getActivity().getApplicationContext()).getAllProfilesSync();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }
}