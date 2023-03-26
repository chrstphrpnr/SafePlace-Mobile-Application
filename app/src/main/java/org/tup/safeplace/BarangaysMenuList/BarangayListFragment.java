package org.tup.safeplace.BarangaysMenuList;

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

public class BarangayListFragment extends Fragment {

    public static ArrayList<Barangay> barangayArrayList = new ArrayList<>();
    ListView listViewBarangay;
    BarangayAdapter adapter;
    Barangay barangay;
    SearchView searchView;
    private View view;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_barangay_list, container, false);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);

        listViewBarangay = view.findViewById(R.id.myListViewBarangay);
        searchView = view.findViewById(R.id.searchBarangay);
        adapter = new BarangayAdapter(getContext(), barangayArrayList);
        listViewBarangay.setAdapter(adapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        listViewBarangay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getContext(), BarangayDetailsActivity.class).putExtra("item", adapter.arrayListBarangayFiltered.get(position)));
            }
        });

        showBarangayList();

        return view;
    }

    private void showBarangayList() {

        dialog.setMessage("Loading...");
        dialog.show();


        StringRequest request = new StringRequest(Request.Method.GET, API.barangay_list_api, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String succes = jsonObject.optString("success");
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                if (succes != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String barangay_name = object.getString("barangay_name");
                        String barangay_captain = object.getString("barangay_captain");
                        String barangay_location = object.getString("barangay_location");
                        String barangay_schedule = object.getString("barangay_schedule");
                        String barangay_contact = object.getString("barangay_contact");
                        String img = object.getString("img");

                        barangay = new Barangay(barangay_name, barangay_captain, barangay_location, barangay_schedule, barangay_contact, img);

                        barangayArrayList.add(barangay);
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
        if(barangayArrayList != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            showBarangayList();
        }
        else{
            barangayArrayList.clear();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.searchBarangay) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}