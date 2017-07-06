package jekirdek.com.t3mobil.activityes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.List;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.utility.JsonParse;
import jekirdek.com.t3mobil.utility.RequestURL;

/**
 * Created by cem
 */
public class DersKatilimFragment extends Fragment {

    private Spinner cmbDers;
    private EditText txtAdSoyadSorgulama;
    private Button btnYoklamaListele;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ders_katilim, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        btnYoklamaListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtAdSoyadSorgulama.getText().equals("") || cmbDers.getSelectedItem().equals("")) {
                    Toast.makeText(getActivity(),"Öğrenci İsmi veya Ders İsmi Eksik",Toast.LENGTH_SHORT).show();
                }else{


                    if (v == null) {
                        //verilen öğrenci yok ise mesaj
                    }else{

                        //Veriler dogru ise sonuç dönecek
                        getTümYoklamaListesi(595);
                    }
                }
            }
        });
    }

    private void getTümYoklamaListesi(int instructorId){
        
        String loginRequestUrl = RequestURL.baseUrl.concat(RequestURL.tümYoklamaListesiUrl).concat(String.valueOf(instructorId)).concat("\"}&date=2017-07-10");
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HATA OLUŞTU");
            }
        });
        System.out.println("request:" + stringRequest.getUrl());
        requestQueue.add(stringRequest);
    }

    private void init(View view){
        cmbDers = (Spinner)view.findViewById(R.id.cmbDers);
        txtAdSoyadSorgulama = (EditText)view.findViewById(R.id.txtAdSoyadSorgulama);
        btnYoklamaListele = (Button)view.findViewById(R.id.btnYoklamaListele);
        getDersListesi();
    }

    private void getDersListesi(){

        String loginRequestUrl = RequestURL.baseUrl.concat(RequestURL.dersListesiUrl);
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginRequestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);
                JsonParse jsonParse = new JsonParse();
                List<String> lessons = jsonParse.getLessonList(response);

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lessons);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                cmbDers.setAdapter(dataAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HATA OLUŞTU");
            }
        });
        System.out.println("request:" + stringRequest.getUrl());
        requestQueue.add(stringRequest);
    }

}
