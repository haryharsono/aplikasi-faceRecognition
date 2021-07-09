package com.example.skripsi.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;
import com.example.skripsi.R;
import com.example.skripsi.adapter.adapterKehadiran;
import com.example.skripsi.adapter.adapterMatkul;
import com.example.skripsi.databinding.FragmentSlideshowBinding;
import com.example.skripsi.model.matkul;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    RecyclerView recyclerView;
    adapterKehadiran adapterKehadiran;
    LinearLayoutManager linearLayoutManager;
    ArrayList<matkul> list;
    DBuser dBuser;
    String[] datalist;
    String id;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        dBuser = new DBuser( root.getContext() );
        datalist = dBuser.getData( FieldUser.NAMA_TABLE );
        id = datalist[0];
        recyclerView = root.findViewById(R.id.daftar_kehadiran_slide_show);
        linearLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        getdata(root.getContext());
        return root;
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

                        list.add(jadwal);
                        adapterKehadiran = new adapterKehadiran(list, context);
                        recyclerView.setAdapter(adapterKehadiran);
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }, error -> {

        }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap=new HashMap<>();
                hashMap.put("id", id);
                return hashMap;

            }
        };
        Volley.newRequestQueue(context).add(stringRequest).setShouldCache(false);
    }
}