package jekirdek.com.t3mobil.activityes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import jekirdek.com.t3mobil.R;
import jekirdek.com.t3mobil.adapter.CustomAdapter;
import jekirdek.com.t3mobil.model.AttendeceListModel;
import jekirdek.com.t3mobil.model.Attendence;
import jekirdek.com.t3mobil.utility.JsonParse;
import jekirdek.com.t3mobil.utility.RequestURL;


/**
 * Created by cem
 */
public class YoklamaAyarFragment extends Fragment {

    private ListView tumOgrenciListView;
    private Button btnKaydet;
    private Attendence[] attendences;
    private CustomAdapter customAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_yoklama_listesi, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        getTumSinifListe();

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * NameSurname ve Id yi map de tutuyoruz ki listviewdan aldığımız name-surname 
                 * göre id alabilelim ve sonuç olarak request atabilelim 
                 */
                
                HashMap<String,String> nameIdMap = new HashMap<String, String>();
                for (int i = 0; i < attendences.length; i++) {
                    nameIdMap.put(attendences[i].getStudentNameSurname(),attendences[i].getId());
                }

                for (int i = 0; i < customAdapter.getCount(); i++) {
                    String studentID = nameIdMap.get(customAdapter.getItem(i).getName());
                    System.out.println("isChecked: " + customAdapter.getItem(i).isChecked());
                    if (customAdapter.getItem(i).isChecked()) {
                        yoklamaAl(Integer.valueOf(studentID), 1);
                    }else{
                        yoklamaAl(Integer.valueOf(studentID), 0);
                    }
                }

                Toast.makeText(getContext().getApplicationContext(),"Yoklama Kaydedildi",Toast.LENGTH_SHORT);
            }
        });
    }

    private void yoklamaAl(int studentId,int presence){
        String yoklamaGuncellemeUrl = RequestURL.getYoklamaGuncellemeUrl(studentId,presence);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, yoklamaGuncellemeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("dönenen sonuc: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    private void getTumSinifListe(){
        String deneyap = getActivity().getIntent().getExtras().getString("deneyap");
        String ders = getActivity().getIntent().getExtras().getString("ders");
        String tarih = getActivity().getIntent().getExtras().getString("tarih");

        String tumOgrenciListesiUrl = RequestURL.getTumOgrenciListesiUrl(1, 1, "2017-07-10");
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplication().getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, tumOgrenciListesiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response: " + response);
                JsonParse jsonParse = new JsonParse();
                attendences = jsonParse.getAttendenceList(response);
                if (jsonParse.getAttendenceList(response).length > 0) {

                    String[] ogrenciList = new String[attendences.length];
                    for (int i = 0;i < attendences.length; i++ ) {
                        ogrenciList[i] = attendences[i].getStudentNameSurname();
                    }

                    ArrayList<AttendeceListModel> ogrenciler = new ArrayList<>();
                    for (int i = 0; i < ogrenciList.length; i++) {
                        AttendeceListModel attendeceListModel = new AttendeceListModel(ogrenciList[i],false);
                        ogrenciler.add(attendeceListModel);
                    }

                    customAdapter = new CustomAdapter(ogrenciler ,getActivity().getApplicationContext());
                    tumOgrenciListView.setAdapter(customAdapter);
                }else{
                    Toast.makeText(getContext(),"Öğrenci Liste Yok",Toast.LENGTH_SHORT).show();
                    System.out.println("Öğrenci Listesi Yok");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("HATA OLUŞTU");
            }
        });
        System.out.println("request:" + request.getUrl());
        requestQueue.add(request);

    }

    private void init(View view){
        tumOgrenciListView = (ListView) view.findViewById(R.id.list);
        btnKaydet = (Button) view.findViewById(R.id.btnYoklamaKaydet);
    }

}
