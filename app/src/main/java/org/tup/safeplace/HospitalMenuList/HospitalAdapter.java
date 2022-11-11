package org.tup.safeplace.HospitalMenuList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.List;

public class HospitalAdapter extends BaseAdapter implements Filterable {

    Context context;
    List<Hospital> hospitalArrayList;
    List<Hospital> hospitalArrayListFiltered;
    LayoutInflater inflater;

    public HospitalAdapter(Context context, List<Hospital> hospitalArrayList) {
        this.context = context;
        this.hospitalArrayList = hospitalArrayList;
        this.hospitalArrayListFiltered = hospitalArrayList;

    }

    @Override
    public int getCount() {
        return hospitalArrayListFiltered.size();
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
        View view = inflater.inflate( R.layout.hospital_list_item, null );

        TextView tvHospitalName = view.findViewById(R.id.txt_hospital_name);
        tvHospitalName.setText(hospitalArrayListFiltered.get(position).getHospital_name());

        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length()>0){
                    ArrayList<Hospital> filterList = new ArrayList<Hospital>();
                    for (int i=0; i<hospitalArrayList.size(); i++){
                        if ((hospitalArrayList.get(i).getHospital_name().toUpperCase()).contains(constraint.toString().toUpperCase())){
                            Hospital hospital = new Hospital(hospitalArrayList.get(i).getHospital_name(),hospitalArrayList.get(i).getHospital_type(),hospitalArrayList.get(i).getHospital_medical_director(),hospitalArrayList.get(i).getHospital_location(),hospitalArrayList.get(i).getHospital_schedule(),hospitalArrayList.get(i).getHospital_contact());
                            filterList.add(hospital);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                }
                else{
                    results.count = hospitalArrayList.size();
                    results.values = hospitalArrayList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                hospitalArrayListFiltered = (List<Hospital>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}

