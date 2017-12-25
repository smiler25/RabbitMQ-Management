package com.smiler.rabbitadministration.queues;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.BaseViewModel;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.detail.QueueInfo;
import com.smiler.rabbitadministration.queues.filter.Filter;
import com.smiler.rabbitadministration.queues.sort.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

public class QueuesListViewModel extends BaseViewModel<ArrayList<QueueInfo>> {
    @Nullable @Getter @Setter
    private Filter filter;
    @Nullable @Getter @Setter
    private Sort sort;

    protected void loadData(ManagementApplication context) {
        QueuesListApi.getList(context, new BaseApi.ApiCallback<ArrayList<QueueInfo>>() {
            @Override
            public void onResult(ArrayList<QueueInfo> result) {
                if (result != null) {
                    new PrepareTask().execute(result);
//                    data.setValue(getSortedData(getFilteredData(result)));
                }
            }

            @Override
            public void onError(String msg) {
                errorMessage.setValue(msg);
            }
        });
    }

    private ArrayList<QueueInfo> getFilteredData(ArrayList<QueueInfo> data) {
        if (filter == null || filter.getValue().isEmpty()) {
            return data;
        }
        String filterValue = filter.getValue();
        if (filter.isRegex()) {
            Pattern p = Pattern.compile(filterValue);
            return new ArrayList<QueueInfo>() {{
                for (QueueInfo one : data) {
                    if (p.matcher(one.getName()).find()) {
                        add(one);
                    }
                }
            }};
        }
        return new ArrayList<QueueInfo>() {{
            for (QueueInfo one : data) {
                if (one.getName().contains(filterValue)) {
                    add(one);
                }
            }
        }};
    }

    private ArrayList<QueueInfo> getSortedData(ArrayList<QueueInfo> data) {
        if (sort == null) {
            return data;
        }

        if (sort.getAscending()) {
            switch (sort.getType()) {
                case NAME:
                    Collections.sort(data, (o1, o2) -> o1.getName().compareTo(o2.getName()));
                case READY:
                    Collections.sort(data, (o1, o2) -> o1.getReady().compareTo(o2.getReady()));
                case UNACKED:
                    Collections.sort(data, (o1, o2) -> o1.getUnacked().compareTo(o2.getUnacked()));
                case TOTAL:
                    Collections.sort(data, (o1, o2) -> o1.getTotal().compareTo(o2.getTotal()));
            }
        } else {
            switch (sort.getType()) {
                case NAME:
                    Collections.sort(data, (o1, o2) -> o2.getName().compareTo(o1.getName()));
                case READY:
                    Collections.sort(data, (o1, o2) -> o2.getReady().compareTo(o1.getReady()));
                case UNACKED:
                    Collections.sort(data, (o1, o2) -> o2.getUnacked().compareTo(o1.getUnacked()));
                case TOTAL:
                    Collections.sort(data, (o1, o2) -> o2.getTotal().compareTo(o1.getTotal()));
            }
        }
        return data;
    }

    void setFilter(ManagementApplication context, Filter newFilter) {
        if (filter == null || filter.getValue().isEmpty()) {
            filter = newFilter;
            data.setValue(getFilteredData(data.getValue()));
        } else {
            filter = newFilter;
            loadData(context);
        }
    }

    void setOrder(@Nullable Sort newSort) {
        sort = newSort;
        if (data.getValue() != null) {
            data.setValue(getSortedData(data.getValue()));
        }
    }

    class PrepareTask extends AsyncTask<ArrayList<QueueInfo>, Void, ArrayList<QueueInfo>> {
        @SafeVarargs
        @Override
        protected final ArrayList<QueueInfo> doInBackground(ArrayList<QueueInfo>... data) {
            return getSortedData(getFilteredData(data[0]));
        }

        @Override
        protected void onPostExecute(ArrayList<QueueInfo> result) {
            super.onPostExecute(result);
            data.setValue(result);
        }
    }
}
