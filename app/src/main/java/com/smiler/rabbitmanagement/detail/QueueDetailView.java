package com.smiler.rabbitmanagement.detail;


import android.content.Context;
import android.widget.LinearLayout;

public class QueueDetailView extends LinearLayout {
    public static String TAG = "RMQ-QueueDetailView";

    public QueueDetailView(Context context, int queue) {
        super(context);
        init(queue);
    }

    private void init(int queue) {
//        inflate(getContext(), R.layout.detail_scroll_view, this);
//        TextView title = findViewById(R.id.detail_scroll_view_title);
//        LinearLayout layout = findViewById(R.id.container);
//        getData(queue);
    }

//    public void setTitle(String value) {
//        title.setText(value);
//    }

//    private void getData(int queue) {
//        team = realmController.getTeam(queue);
//    }

    public void setListener() {

    }
}
