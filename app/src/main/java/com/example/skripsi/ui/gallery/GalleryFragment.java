package com.example.skripsi.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.skripsi.DBuser.DBuser;
import com.example.skripsi.DBuser.FieldUser;
import com.example.skripsi.EndpointData.Endpoint;
import com.example.skripsi.R;
import com.example.skripsi.databinding.FragmentGalleryBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    DBuser dBuser;
    String datalist[], id;
    TextView nip;
    TextView nama;
    TextView alamat;
    TextView noHp;
    TextView password;
    TextView kelas;
    TextView semester;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        nip=root.findViewById(R.id.nip_profile);
        nama=root.findViewById(R.id.nama_profile);
        alamat=root.findViewById(R.id.alamat_profile);
        noHp=root.findViewById(R.id.no_hp_profile);
        password=root.findViewById(R.id.password_profile);
        kelas=root.findViewById(R.id.profile_kelas);
        semester=root.findViewById(R.id.profile_semester);
        dBuser = new DBuser(root.getContext());

        datalist = dBuser.getData(FieldUser.NAMA_TABLE);
        id = datalist[0];
        getData(root.getContext());

        root.findViewById(R.id.edit_profile_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(root.getContext());
            }
        });
        return root;

    }
    public void getData(Context context){
        String url= Endpoint.URL+Endpoint.PROFILE_MAHASISWA;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean status = jsonObject.getBoolean("error");
                if (!status) {

                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject jsonObject_beda = data.getJSONObject(i);

                        nip.setText(jsonObject_beda.get("nim").toString());
                        nama.setText(jsonObject_beda.get("nama").toString());
                        kelas.setText(jsonObject_beda.get("kelas").toString());
                        alamat.setText(jsonObject_beda.get("alamat").toString());
                        password.setText(jsonObject_beda.get("password").toString());
                        noHp.setText(jsonObject_beda.get("no_hp").toString());
                        semester.setText(jsonObject_beda.get("semester").toString());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }, error -> {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);


                return params;
            }

        };
        Volley.newRequestQueue(context).add(stringRequest).setShouldCache(false);
    }
    private void updateProfile(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") final View dialogView = inflater.inflate(R.layout.layout_profile, null);
        builder.setView(dialogView);
        builder.setCancelable(false);
        EditText nip=dialogView.findViewById(R.id.edit_nip_profile);
        EditText nama=dialogView.findViewById(R.id.edit_nama_profile);
        EditText alamat=dialogView.findViewById(R.id.edit_alamat_profile);
        EditText noHp=dialogView.findViewById(R.id.edit_no_hp_profile);
        EditText password=dialogView.findViewById(R.id.edit_password_profile);

        Button ok = dialogView.findViewById(R.id.simpan_profile_button);
        final Button batal = dialogView.findViewById(R.id.profile_batal);
        final AlertDialog dialog = builder.create();

        ok.setOnClickListener(v -> {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://anr.my.id/APIR/edit-profil", response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean("error");
                    if (!status) {
                        Toast.makeText(getActivity(), "EDIT BERHASIL ", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getActivity(), "EDIT GAGAL ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }, error -> {
            }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("nip", nip.getText().toString());
                    params.put("nama", nama.getText().toString());
                    params.put("email", alamat.getText().toString());
                    params.put("no_hp", noHp.getText().toString());
                    params.put("password", password.getText().toString());
                    return params;
                }

            };
            Volley.newRequestQueue(context).add(stringRequest).setShouldCache(false);
            dialog.cancel();
        });

        batal.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

}