package com.example.skripsi.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;
import com.example.skripsi.R;
import com.example.skripsi.adapter.adapterMatkul;
import com.example.skripsi.databinding.FragmentHomeBinding;
import com.example.skripsi.model.matkul;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    adapterMatkul adapterMatkul;
    LinearLayoutManager linearLayoutManager;
    ArrayList<matkul> list;
    DBuser dBuser;
    String[] datalist;
    String id;
    EditText cari;
    SwipeRefreshLayout swipeRefreshLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        cari=root.findViewById(R.id.cari);

        dBuser = new DBuser( root.getContext() );
        datalist = dBuser.getData( FieldUser.NAMA_TABLE );
        id = datalist[0];
        recyclerView = root.findViewById(R.id.recycle_matkul);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        swipeRefreshLayout=root.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getdata(getContext());
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });


        cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        getdata(getContext());
        return root;
    }
    private void filter(String text) {
        ArrayList<matkul> filteredList = new ArrayList<>();
            for (matkul item : list) {
            if (item.getNamaMatkul().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapterMatkul.filterList(filteredList);
    }

    void getdata(Context context){
        String url= Endpoint.URL+Endpoint.CEK_JADWAL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("error");
                if (!status){
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        matkul jadwal = new matkul();

                            jadwal.setNamaMatkul(jsonObject1.getString("matkul"));
                            jadwal.setPukulMulai(jsonObject1.getString("waktu_mulai"));
                            jadwal.setPukulSelesai(jsonObject1.getString("waktu_selesai"));
                            jadwal.setDosen(jsonObject1.getString("nama_dosen"));
                            jadwal.setHari(jsonObject1.getString("hari"));
//                            jadwal.setTanggal(jsonObject1.getString("Tanggal"));
                            jadwal.setZoom(jsonObject1.getString("zoom"));
                        jadwal.setZoom(jsonObject1.getString("id_zoom"));
                        jadwal.setZoom(jsonObject1.getString("password_zoom"));
                            list.add(jadwal);
                            adapterMatkul = new adapterMatkul(list, context);
                            recyclerView.setAdapter(adapterMatkul);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }, error -> {

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap=new HashMap<>();
                  hashMap.put("id", id);
                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(stringRequest).setShouldCache(false);
    }
}