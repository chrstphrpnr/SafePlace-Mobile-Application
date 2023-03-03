package org.tup.safeplace.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.tup.safeplace.R;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<NotificationModel> {

    Context context;
    List<NotificationModel> arrayListNotification;

    public NotificationAdapter(@NonNull Context context, List<NotificationModel> arrayListNotification) {
        super(context, R.layout.notification_list_item, arrayListNotification);

        this.context = context;
        this.arrayListNotification = arrayListNotification;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item,null,true);

        TextView tvMessage = view.findViewById(R.id.txt_notification);
        TextView tvStatus = view.findViewById(R.id.txt_notification_status);


        tvMessage.setText(arrayListNotification.get(position).getMessage());
        tvStatus.setText(arrayListNotification.get(position).getStatus());


        if (tvStatus.getText().equals("unread")){
            view.setBackgroundResource(android.R.color.darker_gray);
        }

        if (tvStatus.getText().equals("read")){
            view.setBackgroundResource(R.color.white);
        }








        return view;


    }
}
