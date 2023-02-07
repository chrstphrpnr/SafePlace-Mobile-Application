package org.tup.safeplace.HospitalMenuList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

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

public class HospitalListMenuFragment extends Fragment {
    private View view;
    SearchView searchHospital;
    ListView listView;
    HospitalAdapter adapter;
    public static ArrayList<Hospital> hospitalArrayList = new ArrayList<>();
    Hospital hospital;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hospital_list_menu, container, false);
        listView = view.findViewById(R.id.myListView);
        searchHospital = view.findViewById(R.id.searchHospital);
        adapter =new HospitalAdapter(getContext(),hospitalArrayList);
        listView.setAdapter(adapter);

        searchHospital.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getContext(),HospitalDetailActivity.class).putExtra("item",adapter.hospitalArrayListFiltered.get(position)));
            }
        });
        
        showHospitalList();
        
        return view;
    }

    private void showHospitalList() {
        StringRequest request = new StringRequest(Request.Method.GET, API.hospital_list_api, response -> {
            hospitalArrayList.clear();

            try {
                JSONObject jsonObject =new JSONObject(response);
                String succes = jsonObject.optString("success");
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                if(succes!=null){
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String hospital_name = object.getString("hospital_name");
                        String hospital_type = object.getString("hospital_type");
                        String hospital_medical_director = object.getString("hospital_medical_director");
                        String hospital_location = object.getString("hospital_location");
                        String hospital_schedule = object.getString("hospital_schedule");
                        String hospital_contact = object.getString("hospital_contact");
                        String img = object.getString("img");


                        hospital = new Hospital(hospital_name,hospital_type,hospital_medical_director,hospital_location,hospital_schedule,hospital_contact,img);

                        hospitalArrayList.add(hospital);
                        adapter.notifyDataSetChanged();
                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.searchHospital){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
