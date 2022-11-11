package org.tup.safeplace.PoliceStationMenuList;

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

import org.tup.safeplace.R;

import java.util.ArrayList;
import java.util.List;

public class PoliceStationAdapter extends BaseAdapter implements Filterable {
    Context context;
    List<PoliceStation> arrayListPoliceStation;
    List<PoliceStation> arrayListPoliceStationFiltered;
    LayoutInflater inflater;

    public PoliceStationAdapter(Context context, List<PoliceStation> arrayListPoliceStation) {
        this.context = context;
        this.arrayListPoliceStation = arrayListPoliceStation;
        this.arrayListPoliceStationFiltered = arrayListPoliceStation;

    }

    @Override
    public int getCount() {
        return arrayListPoliceStationFiltered.size();
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
        View view = inflater.inflate( R.layout.policestation_list_item, null );

        TextView tvPoliceStationName = view.findViewById(R.id.txt_policestation_name);
        tvPoliceStationName.setText(arrayListPoliceStationFiltered.get(position).getPolicestation_name());


        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null && constraint.length()>0){
                    ArrayList<PoliceStation> filterList = new ArrayList<PoliceStation>();
                    for (int i=0; i<arrayListPoliceStation.size(); i++){
                        if ((arrayListPoliceStation.get(i).getPolicestation_name().toUpperCase()).contains(constraint.toString().toUpperCase())){

                            PoliceStation policeStation = new PoliceStation(
                                    arrayListPoliceStation.get(i).getPolicestation_name(),
                                    arrayListPoliceStation.get(i).getPolicestation_commander(),
                                    arrayListPoliceStation.get(i).getPolicestation_location(),
                                    arrayListPoliceStation.get(i).getPolicestation_schedule(),
                                    arrayListPoliceStation.get(i).getPolicestation_contact()
                            );

                            filterList.add(policeStation);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                }
                else{
                    results.count = arrayListPoliceStation.size();
                    results.values = arrayListPoliceStation;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                arrayListPoliceStationFiltered = (List<PoliceStation>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }
}
