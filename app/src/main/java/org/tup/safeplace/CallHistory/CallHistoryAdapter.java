package org.tup.safeplace.CallHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.tup.safeplace.R;

import java.util.List;

public class CallHistoryAdapter extends BaseAdapter {

    Context context;
    List<CallHistory> arrayListCallBarangay;

    public CallHistoryAdapter(Context context, List<CallHistory> arrayListCallBarangay) {
        this.context = context;
        this.arrayListCallBarangay = arrayListCallBarangay;
    }

    @Override
    public int getCount() {
        return arrayListCallBarangay.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.call_history_list_item, null);

        ImageView barangay_ic = view.findViewById(R.id.barangay_icon);

        String type = arrayListCallBarangay.get(position).getType_contacted();

        if (type.equals("barangay")) {
            barangay_ic.setImageResource(R.drawable.ic_barangay_red);

        } else {

            barangay_ic.setImageResource(R.drawable.ic_police_blue);

        }


        TextView tvBarangayname = view.findViewById(R.id.txt_barangay_name_call);
        TextView txt_barangay_call_date = view.findViewById(R.id.txt_barangay_call_date);
        TextView txt_barangay_call_time = view.findViewById(R.id.txt_barangay_call_time);

        tvBarangayname.setText(arrayListCallBarangay.get(position).getName_contacted());
        txt_barangay_call_date.setText(arrayListCallBarangay.get(position).getDate_contacted());
        txt_barangay_call_time.setText(arrayListCallBarangay.get(position).getTime_contacted());

        return view;
    }
}
