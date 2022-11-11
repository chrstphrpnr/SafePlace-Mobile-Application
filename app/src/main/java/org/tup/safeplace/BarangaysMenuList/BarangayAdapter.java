package org.tup.safeplace.BarangaysMenuList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.tup.safeplace.BarangaysMenuList.Barangay;
import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.List;

public class BarangayAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<Barangay> arrayListBarangay;
    List<Barangay> arrayListBarangayFiltered;
    LayoutInflater inflater;

    public BarangayAdapter(Context context, List<Barangay> arrayListBarangay) {
        this.context = context;
        this.arrayListBarangay = arrayListBarangay;
        this.arrayListBarangayFiltered = arrayListBarangay;
    }

    @Override
    public int getCount() {
        return arrayListBarangayFiltered.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate( R.layout.barangay_list_menu_item, null );

        TextView tvBarangayname = view.findViewById(R.id.txt_barangay_name);
        tvBarangayname.setText(arrayListBarangayFiltered.get(position).getBarangay_name());


        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length()>0){
                    ArrayList<Barangay> filterList = new ArrayList<Barangay>();
                    for (int i=0; i<arrayListBarangay.size(); i++){
                        if ((arrayListBarangay.get(i).getBarangay_name().toUpperCase()).contains(constraint.toString().toUpperCase())){
                            Barangay barangay = new Barangay(arrayListBarangay.get(i).getBarangay_name(),arrayListBarangay.get(i).getBarangay_captain(),arrayListBarangay.get(i).getBarangay_location(),arrayListBarangay.get(i).getBarangay_schedule(),arrayListBarangay.get(i).getBarangay_contact());
                            filterList.add(barangay);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                }
                else{
                    results.count = arrayListBarangay.size();
                    results.values = arrayListBarangay;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                arrayListBarangayFiltered = (List<Barangay>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
