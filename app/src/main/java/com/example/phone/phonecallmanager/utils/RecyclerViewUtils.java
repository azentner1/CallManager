package com.example.phone.phonecallmanager.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.enums.EmptyViewMessage;

public class RecyclerViewUtils {

    public static View getEmptyView(Context context, ViewGroup parent, EmptyViewMessage emptyViewMessage) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_empty_view, parent, false);
        TextView tvEmptyViewMessage = (TextView) view.findViewById(R.id.tvEmptyViewMessage);
        String message = "";
        if (emptyViewMessage.equals(EmptyViewMessage.LOG)) {
            message = context.getString(R.string.empty_view_message_log);
        } else if (emptyViewMessage.equals(EmptyViewMessage.LIST)) {
            message = context.getString(R.string.empty_view_message_list);
        } else {
            tvEmptyViewMessage.setVisibility(View.GONE);
        }
        tvEmptyViewMessage.setText(message);
        return view;
    }
}
