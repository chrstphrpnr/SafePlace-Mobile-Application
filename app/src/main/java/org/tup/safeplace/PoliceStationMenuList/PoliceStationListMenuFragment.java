package org.tup.safeplace.PoliceStationMenuList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.tup.safeplace.Constants.API;
import org.tup.safeplace.R;

import java.util.ArrayList;

public class PoliceStationListMenuFragment extends Fragment {

    public static ArrayList<PoliceStation> policeStationArrayList = new ArrayList<>();
    ListView listViewPolice;
    PoliceStationAdapter adapter;
    PoliceStation policeStation;
    SearchView searchPoliceStation;
    private View view;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_police_station_list_menu, container, false);
        searchPoliceStation = view.findViewById(R.id.searchPoliceStation);
        listViewPolice = view.findViewById(R.id.myListViewPoliceStation);
        adapter = new PoliceStationAdapter(getContext(), policeStationArrayList);
        listViewPolice.setAdapter(adapter);


        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);


        searchPoliceStation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listViewPolice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getContext(), PoliceStationDetailActivity.class).putExtra("item", adapter.arrayListPoliceStationFiltered.get(position)));
            }
        });

        showPoliceStationList();

        return view;
    }

    private void showPoliceStationList() {

        dialog.setMessage("Loading...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.GET, API.police_stations_list_api, response -> {
            policeStationArrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(response);
                String succes = jsonObject.optString("success");
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                if (succes != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String policestation_name = object.getString("policestation_name");
                        String policestation_commander = object.getString("policestation_commander");
                        String policestation_location = object.getString("policestation_location");
                        String policestation_schedule = object.getString("policestation_schedule");
                        String policestation_contact = object.getString("policestation_contact");
                        String img = object.getString("img");


                        policeStation = new PoliceStation(policestation_name, policestation_commander, policestation_location, policestation_schedule, policestation_contact, img);

                        policeStationArrayList.add(policeStation);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                dialog.dismiss();


            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(policeStationArrayList != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            showPoliceStationList();
        }
        else{
            policeStationArrayList.clear();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchPoliceStation) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}